plugins {
    kotlin("jvm") version "1.9.22"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "com.nikhiljha"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    implementation("org.apache.guacamole:guacamole-ext:1.5.4")
}

tasks.shadowJar {
    append("guac-manifest.json")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}