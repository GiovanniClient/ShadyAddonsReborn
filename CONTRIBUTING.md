## For Developers!
If you wanna run/edit/compile yourself this mod here's how to
- Install both JDK 8 and JDK 17(or newer)
- Clone the repo
- Open the project in Intellij
- Let it cook (it needs to sync the project and index files, watch bottom right)
- Once it's done, close and reopen the IDE (you **must**)
- CTRL + ALT + SHIFT + S -> In *SDK*, select you JDK 8
- CTRL + ALT + S -> *Build, Execution and Enviroment* -> *Build Tools* -> *Gradle* -> *Gradle JVM* -> Select your JDK 17 (or newer)
- In the top of the IDE, locate *"Minecraft Client (:1.8.9-forge)"*, click and select *Edit Configurations...*, just click *Apply* in the bottom and *Ok*
- You can now run by clicking the green arrow in the top

### Optional
- You can setup [DevAuth](https://github.com/DJtheRedstoner/DevAuth) to log in with your Minecraft account

### Compiling a .jar
- Open a Terminal in the bottom left
- run `./gradlew build --no-daemon`
- now navigate to `<project root>\versions\1.8.9-forge\build\libs`, the .jar **without** the `-dev` is what you should use
