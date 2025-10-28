https://stonecutter.kikugie.dev/

https://github.com/Fallen-Breath/conditional-mixin

Build all versions:
```bash
./gradlew buildAndCollect
```
Generate data packs for all versions:
```bash
./gradlew runDatagen
```
Change minecraft version environment
```bash
./gradlew "Set active project to 1.21.6"
```
Run a specific minecraft version (must be in environment)
```bash
./gradlew :1.21.6:runClient
```
Build specific version
```bash
./gradlew :1.21.6:build
```
Publish mods
**MAKE SURE TO UPDATE DESCRIPTION IN build.gradle**
```bash
./gradlew publishMods
```