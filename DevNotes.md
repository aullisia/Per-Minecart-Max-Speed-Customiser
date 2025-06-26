https://stonecutter.kikugie.dev/

https://github.com/Fallen-Breath/conditional-mixin

Build All versions:
```bash
./gradlew chiseledBuildAndCollect
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
./gradlew :1.21.5:build
```

.env
```
MODRINTH_TOKEN=xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
```