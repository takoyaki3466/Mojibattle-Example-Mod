plugins {
    java
}

group = "com.example"
version = "1.0.0"

repositories {
    mavenLocal()
    mavenCentral()
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

dependencies {
    compileOnly("jp.takoa.mojibattle:mojibattle-api:0.4.0")
}