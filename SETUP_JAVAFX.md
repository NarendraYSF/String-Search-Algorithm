# JavaFX Setup Guide for Pattern Search GUI

## Prerequisites

The JavaFX GUI version requires JavaFX SDK to be installed separately as it's not included in standard JDK distributions since Java 11.

## Installation Steps

### Option 1: Using JavaFX SDK (Recommended for Standalone)

1. **Download JavaFX SDK:**
   - Visit: https://gluonhq.com/products/javafx/
   - Download the JavaFX SDK for your platform (Windows, Mac, or Linux)
   - Extract to a location, e.g., `C:\Program Files\Java\javafx-sdk-21`

2. **Compile the Program:**
   ```bash
   javac --module-path "C:\Program Files\Java\javafx-sdk-21\lib" --add-modules javafx.controls,javafx.graphics PatternSearchGUI.java
   ```

3. **Run the Program:**
   ```bash
   java --module-path "C:\Program Files\Java\javafx-sdk-21\lib" --add-modules javafx.controls,javafx.graphics PatternSearchGUI
   ```

### Option 2: Using Maven (Recommended for Projects)

1. **Create a `pom.xml` file:**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
         http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.learning</groupId>
    <artifactId>pattern-search</artifactId>
    <version>1.0</version>

    <properties>
        <maven.compiler.source>11</maven.compiler.source>
        <maven.compiler.target>11</maven.compiler.target>
        <javafx.version>21</javafx.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.openjfx</groupId>
            <artifactId>javafx-controls</artifactId>
            <version>${javafx.version}</version>
        </dependency>
        <dependency>
            <groupId>org.openjfx</groupId>
            <artifactId>javafx-graphics</artifactId>
            <version>${javafx.version}</version>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.openjfx</groupId>
                <artifactId>javafx-maven-plugin</artifactId>
                <version>0.0.8</version>
                <configuration>
                    <mainClass>PatternSearchGUI</mainClass>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```

2. **Run with Maven:**
   ```bash
   mvn clean javafx:run
   ```

### Option 3: Using Gradle

1. **Create a `build.gradle` file:**

```gradle
plugins {
    id 'application'
    id 'org.openjfx.javafxplugin' version '0.0.13'
}

group 'com.learning'
version '1.0'

repositories {
    mavenCentral()
}

javafx {
    version = "21"
    modules = [ 'javafx.controls', 'javafx.graphics' ]
}

application {
    mainClass = 'PatternSearchGUI'
}
```

2. **Run with Gradle:**
   ```bash
   gradle run
   ```

## Quick Setup Script (Windows)

Save this as `run_gui.bat`:

```batch
@echo off
SET JAVAFX_PATH=C:\Program Files\Java\javafx-sdk-21\lib

echo Compiling JavaFX GUI...
javac --module-path "%JAVAFX_PATH%" --add-modules javafx.controls,javafx.graphics PatternSearchGUI.java

if %ERRORLEVEL% EQU 0 (
    echo Compilation successful! Running application...
    java --module-path "%JAVAFX_PATH%" --add-modules javafx.controls,javafx.graphics PatternSearchGUI
) else (
    echo Compilation failed!
    pause
)
```

## Quick Setup Script (Linux/Mac)

Save this as `run_gui.sh`:

```bash
#!/bin/bash
JAVAFX_PATH="/path/to/javafx-sdk-21/lib"

echo "Compiling JavaFX GUI..."
javac --module-path "$JAVAFX_PATH" --add-modules javafx.controls,javafx.graphics PatternSearchGUI.java

if [ $? -eq 0 ]; then
    echo "Compilation successful! Running application..."
    java --module-path "$JAVAFX_PATH" --add-modules javafx.controls,javafx.graphics PatternSearchGUI
else
    echo "Compilation failed!"
fi
```

Make it executable:
```bash
chmod +x run_gui.sh
./run_gui.sh
```

## Troubleshooting

### Error: "module not found: javafx.controls"
- JavaFX SDK is not installed or path is incorrect
- Download from https://gluonhq.com/products/javafx/
- Update the path in your compile/run commands

### Error: "Error: JavaFX runtime components are missing"
- You compiled with JavaFX but trying to run without it
- Make sure to include `--module-path` and `--add-modules` in your run command

### Error: "Graphics Device initialization failed"
- Your system might not support JavaFX graphics
- Try updating your graphics drivers
- On Linux, ensure you have the required graphics libraries installed

## Alternative: Use the Terminal Version

If JavaFX setup is too complex, you can use the terminal version instead:

```bash
# Compile and run the terminal version
javac PatternSearch.java
java PatternSearch
```

Or use the Python GUI version:
```bash
python pattern_search_gui.py
```

## System Requirements

- Java 11 or higher
- JavaFX SDK 11 or higher
- Windows 10/11, macOS 10.12+, or Linux with X11
- At least 512MB RAM
- Graphics card with OpenGL 2.0 support

## Features of the JavaFX GUI

✅ Visual character-by-character comparison  
✅ Animated search process  
✅ Adjustable animation speed  
✅ Case-sensitive/insensitive search  
✅ Pre-loaded examples  
✅ Real-time status updates  
✅ Color-coded visualization  
✅ Interactive controls  

## Need Help?

- JavaFX Documentation: https://openjfx.io/
- Getting Started: https://openjfx.io/openjfx-docs/
- Maven Central: https://mvnrepository.com/artifact/org.openjfx

