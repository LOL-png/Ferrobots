# ferroBotApp

Version 2.0

## Version History

| Version | Date       | Update          | Author    |
|---------|------------|-----------------|-----------|
| 1.0     | 7/30/2023  | Initial version | Howard Ji |
| 2.0     | 9/15/2023  | Release version | Howard Ji |

## 1. Install JAVA Runtime Environment

FerroBot Application for Simulation is developed with JAVA, so JAVA runtime environment is required to be installed to run the application.

First check whether JAVA JDK is already installed with the required version or not.

Type this line into the terminal:
```bash
java -version
```

Your output should look something like this:
```
java version "20.0.1" 2023-04-18
Java(TM) SE Runtime Environment (build 20.0.1+9-29)
Java HotSpot(TM) 64-Bit Server VM (build 20.0.1+9-29, mixed mode, sharing)
```

If the Java version shows 20.0.1 or above, then, the Java runtime environment is ready and does not need to be installed. If the return says Java cannot be found, or there is an incorrect version number, proceed with the following installation steps.

### 1.1 Install Java

- **Download link:** https://www.oracle.com/java/technologies/downloads/#jdk20-mac
- **Installation Document:** https://docs.oracle.com/en/java/javase/20/install/installation-jdk-macos.html#GUID-2FE451B0-9572-4E38-A1A5-568B77B146DE

## 2. Download ferroBotApp

Ferrobotapp download link:
https://drive.google.com/drive/folders/1MNJ8k1XyMmyf7TKOADrUZuUwkmFdRXeS?usp=sharing

### 2.1 Run ferroBotApp

Download the zipped folder and unzip it. Do NOT modify any folder contents. Type the following into the terminal:

```bash
java -jar --enable-preview ferrobotapp.jar
```

## 3. Adjustment for different platforms

The font might need to be adjusted to get a perfect interface on different platforms. For macs, it might need a smaller font.