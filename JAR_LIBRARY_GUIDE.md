# JAR Library Integration Guide

## Current Project Structure
```
blockchain-java/
├── lib/                    # JAR libraries folder
│   └── gson-2.6.2.jar     # Google Gson JSON library
├── src/                    # Source code
│   ├── App.java           # Main application
│   └── Block.java         # Block class
└── JAR_LIBRARY_GUIDE.md   # This guide
```

## Method 1: Using lib/ Folder (Current Setup)

### Adding New JAR Libraries
1. Download the JAR file you want to use
2. Place it in the `lib/` folder
3. Import the classes in your Java files

### Compilation and Execution
```bash
# Compile with JAR libraries
javac -cp "lib/*:src" src/*.java

# Run with JAR libraries
java -cp "lib/*:src" App
```

### Example: Using Gson Library
```java
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

// Code Snippet:
Gson gson = new GsonBuilder().setPrettyPrinting().create();
String json = gson.toJson(yourObject);
```

## Method 2: Direct JAR Path

### Single JAR
```bash
# Compile
javac -cp "/path/to/library.jar:src" src/*.java

# Run
java -cp "/path/to/library.jar:src" App
```

### Multiple JARs
```bash
# Compile 
cd /home/tsilva/code/blockchain-java && javac -cp "lib/*:src" src/*.java

# Run
cd /home/tsilva/code/blockchain-java && java -cp "lib/*:src" Blockchain

# Compile and Run
cd /home/tsilva/code/blockchain-java && javac -cp "lib/*:src" src/*.java && java -cp "lib/*:src" Blockchain
```

## Method 3: Using Maven (Recommended for Large Projects)

### Create pom.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
         http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    
    <groupId>com.example</groupId>
    <artifactId>blockchain-java</artifactId>
    <version>1.0.0</version>
    
    <properties>
        <maven.compiler.source>11</maven.compiler.source>
        <maven.compiler.target>11</maven.compiler.target>
    </properties>
    
    <dependencies>
        <dependency>
            <groupId>com.google.code.gson</groupId>
            <artifactId>gson</artifactId>
            <version>2.8.9</version>
        </dependency>
    </dependencies>
</project>
```

### Maven Commands
```bash
# Compile
mvn compile

# Run
mvn exec:java -Dexec.mainClass="App"

# Package
mvn package
```

## Method 4: Using Gradle

### Create build.gradle
```gradle
plugins {
    id 'java'
    id 'application'
}

repositories {
    mavenCentral()
}

dependencies {
    implementation 'com.google.code.gson:gson:2.8.9'
}

application {
    mainClass = 'App'
}
```

### Gradle Commands
```bash
# Compile
gradle build

# Run
gradle run
```

## Popular JAR Libraries for Blockchain Projects

### JSON Processing
- **Gson**: `com.google.code.gson:gson`
- **Jackson**: `com.fasterxml.jackson.core:jackson-core`

### Cryptography
- **Bouncy Castle**: `org.bouncycastle:bcprov-jdk15on`

### HTTP Client
- **OkHttp**: `com.squareup.okhttp3:okhttp`
- **Apache HttpClient**: `org.apache.httpcomponents:httpclient`

### Database
- **SQLite JDBC**: `org.xerial:sqlite-jdbc`
- **H2 Database**: `com.h2database:h2`

### Testing
- **JUnit**: `junit:junit`
- **Mockito**: `org.mockito:mockito-core`

## VS Code Configuration

### Create .vscode/settings.json
```json
{
    "java.project.sourcePaths": ["src"],
    "java.project.outputPath": "bin",
    "java.project.referencedLibraries": [
        "lib/**/*.jar"
    ]
}
```

### Create .classpath (for Eclipse integration)
```xml
<?xml version="1.0" encoding="UTF-8"?>
<classpath>
    <classpathentry kind="src" path="src"/>
    <classpathentry kind="lib" path="lib/gson-2.6.2.jar"/>
    <classpathentry kind="con" path="org.eclipse.jdt.launching.JRE_CONTAINER"/>
    <classpathentry kind="output" path="bin"/>
</classpath>
```

## Common Issues and Solutions

### Issue: ClassNotFoundException
**Solution**: Ensure the JAR is in the classpath when running
```bash
java -cp "lib/*:src" App
```

### Issue: Package does not exist
**Solution**: Ensure the JAR is in the classpath when compiling
```bash
javac -cp "lib/*:src" src/*.java
```

### Issue: NoClassDefFoundError
**Solution**: Check for missing dependencies or version conflicts

## Best Practices

1. **Version Management**: Keep track of JAR versions in documentation
2. **Dependency Conflicts**: Avoid multiple versions of the same library
3. **Security**: Only use trusted libraries from reputable sources
4. **Updates**: Regularly update libraries for security patches
5. **Documentation**: Document all external dependencies

## Current Working Example

Your project is already set up with Gson library. You can:

1. Add more JARs to the `lib/` folder
2. Import the classes in your Java files
3. Compile with: `javac -cp "lib/*:src" src/*.java`
4. Run with: `java -cp "lib/*:src" App`

The Gson library is already working and converting your blockchain to JSON format!
