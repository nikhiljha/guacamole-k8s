plugins {
    kotlin("jvm") version "1.9.22"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "com.nikhiljha"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    // maven { url = uri("https://jitpack.io") }
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    implementation("org.apache.guacamole:guacamole-ext:1.5.4")

    implementation("io.fabric8:kubernetes-client:6.10.0")
    // This DSL is nice but I don't need to use it.
    // implementation("com.github.fkorotkov:k8s-kotlin-dsl:3.3.0")
}

tasks.shadowJar {
    append("guac-manifest.json")
    dependencies {
        exclude(dependency("org.apache.guacamole::"))
        exclude(dependency("org.slf4j:slf4j-api::"))
    }
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8)
}