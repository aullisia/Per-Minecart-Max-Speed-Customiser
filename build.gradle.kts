plugins {
    // This plugin applies the correct loom variant based on the Minecraft version
    id("dev.kikugie.loom-back-compat")
    id("me.modmuss50.mod-publish-plugin") version "0.8.4"
}

// DO NOT set group = ...!
version = "${property("mod.version")}+${sc.current.version}"
base.archivesName = property("mod.id") as String

val requiredJava: JavaVersion = when {
    sc.current.parsed >= "26.1" -> JavaVersion.VERSION_25
    sc.current.parsed >= "1.20.5" -> JavaVersion.VERSION_21
    sc.current.parsed >= "1.18" -> JavaVersion.VERSION_17
    sc.current.parsed >= "1.17" -> JavaVersion.VERSION_16
    else -> JavaVersion.VERSION_1_8
}

// This can be used for publishing on Modrinth and Curseforge
val compatibleVersions: List<String> = sc.properties.rawOrNull("mod", "mc_releases")
    ?.asList().orEmpty().map { it.toString() }

repositories {
    /**
     * Restricts dependency search of the given [groups] to the [maven URL][url],
     * improving the setup speed.
     */
    fun strictMaven(url: String, alias: String, vararg groups: String) = exclusiveContent {
        forRepository { maven(url) { name = alias } }
        filter { groups.forEach(::includeGroup) }
    }
    strictMaven(
        "https://www.cursemaven.com",
        "CurseForge",
        "curse.maven"
    )

    strictMaven(
        "https://api.modrinth.com/maven",
        "Modrinth",
        "maven.modrinth"
    )

    maven("https://jitpack.io") {
        name = "JitPack"
    }
}

dependencies {
    /**
     * The full `fabric-api` jar is used instead of individual modules because the mod's
     * fabric.mod.json hard-requires the `fabric-api` (and `fabric`) mod ids at boot.
     * Depending only on individual modules does not register those ids, so the game
     * would refuse to start.
     * @see <a href="https://github.com/FabricMC/fabric">List of Fabric API modules</a>
     */
    minecraft("com.mojang:minecraft:${sc.current.version}")
    // Applies Mojang Mappings on obfuscated versions
    loomx.applyMojangMappings()

    // Use `mod{dependency type}` even on 26.1+ - loom-back-compat converts them
    modImplementation("net.fabricmc:fabric-loader:${property("deps.fabric_loader")}")
    modImplementation("net.fabricmc.fabric-api:fabric-api:${property("deps.fabric_api")}")

//    modImplementation("com.github.Fallen-Breath:conditional-mixin-fabric:0.6.4")
//    include("com.github.Fallen-Breath:conditional-mixin-fabric:0.6.4")

    modImplementation("curse.maven:supermartijn642s-config-lib-438332:${property("deps.config_m")}")
}

loom {
    fabricModJsonPath = rootProject.file("src/main/resources/fabric.mod.json") // Useful for interface injection
    accessWidenerPath = sc.process(
        rootProject.file("src/main/resources/pmmsc.ct"),
        "build/processed.ct"
    )

    decompilerOptions.named("vineflower") {
        options.put("mark-corresponding-synthetics", "1") // Adds names to lambdas - useful for mixins
    }

    runConfigs.all {
        preferGradleTask = true
        generateRunConfig = true
        runDirectory = rootProject.file("run") // Shares the run directory between versions
        jvmArguments.add("-Dmixin.debug.export=true") // Exports transformed classes for debugging
    }

    // Data generation run configuration (generates the `runDatagen` task).
    // Mirrors what `fabricApi { configureDataGeneration { ... } }` would do so it works
    // with the loom variant selected by loom-back-compat.
    runConfigs.create("datagen") {
        inherit(runConfigs["server"])
        displayName = "Minecraft Data Generation"
        systemProperties.set(
            mapOf(
                "fabric-api.datagen" to "",
                "fabric-api.datagen.output-dir" to rootProject.file("src/main/generated").absolutePath,
                "fabric-api.datagen.modid" to project.property("mod.id") as String,
                "fabric-api.datagen.strict-validation" to "true"
            )
        )
    }
}

// Include fabric-loom generated datagen output in the built jar.
sourceSets["main"].resources.srcDir(rootProject.file("src/main/generated"))

java {
    withSourcesJar()
    targetCompatibility = requiredJava
    sourceCompatibility = requiredJava

    toolchain {
        vendor = JvmVendorSpec.ADOPTIUM
        languageVersion = JavaLanguageVersion.of(requiredJava.majorVersion)
    }
}

tasks {
    processResources {
        fun MutableMap<String, String>.register(key: String, property: String) {
            val value: String = sc.properties[property]
            inputs.property(key, value)
            set(key, value)
        }

        val props = buildMap {
            register("id", "mod.id")
            register("name", "mod.name")
            register("version", "mod.version")
            register("minecraft", "mod.mc_compat")
            register("mcdep", "mod.mc_compat")
            val javaDep = ">=${requiredJava.majorVersion}"
            inputs.property("javadep", javaDep)
            set("javadep", javaDep)
        }

        filesMatching("fabric.mod.json") { expand(props) }

        val mixinJava = "JAVA_${requiredJava.majorVersion}"
        filesMatching("*.mixins.json") { expand("java" to mixinJava) }
    }

    register<Copy>("buildAndCollect") {
        group = "build"
        description = "Builds mod jars and copies results to `build/libs/{mod version}/`"

        inputs.property("version", project.property("mod.version"))
        // loomx.mod(Sources)Jar returns the jar task for the applied loom variant
        from(loomx.modJar.flatMap { it.archiveFile }, loomx.modSourcesJar.flatMap { it.archiveFile })
        into(rootProject.layout.buildDirectory.file("libs/${project.property("mod.version")}"))
    }

    // Exclude the datagen cache dir from the jar to ensure reproducible builds.
    withType<Jar>().configureEach { exclude(".cache/**") }

    // Every version shares the root `run/` directory. The asset/native downloads of one
    // version write into the same `run/` output that another version's run task reads, so
    // declare an explicit dependency to satisfy Gradle's implicit-dependency validation.
    withType<net.fabricmc.loom.task.AbstractRunTask>().configureEach {
        dependsOn(rootProject.subprojects.map { it.tasks.named("downloadAssets") })
    }
}

publishMods {
    val mcVersion = sc.current.version

    version = project.version.toString()

    changelog = """
        - Shiny new UI to configure Max Speed
        - Removed old scroll wheel configuration
        - New server configuration can be found in config/pmmsc-PerMinecartMaxSpeedCustomiser-Common.toml
    """.trimIndent()

    type = STABLE

    file = loomx.modJar.flatMap { it.archiveFile }
    modLoaders.add("fabric")

    modrinth {
        projectId = "GJPp630M"
        accessToken = providers.environmentVariable("MODRINTH_TOKEN")

        minecraftVersions.add(mcVersion)

        requires {
            id = "P7dR8mSH"
        }

        optional {
            id = "LN9BxssP"
        }
    }
}