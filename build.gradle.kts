plugins {
    kotlin("jvm") version "2.1.20"
    id("com.gradleup.shadow") version "9.0.0-beta12"
}

group = "io.github.devalphagot"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://oss.sonatype.org/content/repositories/central")
    maven("https://libraries.minecraft.net")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    maven("https://jitpack.io")
    maven("https://maven.enginehub.org/repo/")
    maven(url = "https://s01.oss.sonatype.org/content/repositories/snapshots/") {
        name = "sonatype-oss-snapshots1"
    }
    maven(url = "https://repo.codemc.io/repository/maven-snapshots/"){
        name = "codemc-snapshots"
    }

    maven(url = "https://raw.githubusercontent.com/kusaanko/maven/main/")
    maven("https://repo.repsy.io/mvn/lone64/platform")
}

dependencies {
    compileOnly(kotlin("stdlib"))
    compileOnly(kotlin("reflect"))

    compileOnly("io.papermc.paper:paper-api:1.17-R0.1-SNAPSHOT")

    implementation("net.kyori:adventure-text-minimessage:4.14.0")

    // compileOnly("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")

    compileOnly("io.reactivex.rxjava2:rxjava:2.1.16")
    compileOnly("io.socket:socket.io-client:1.0.0")
    compileOnly("org.jsoup:jsoup:1.13.1")
    compileOnly("com.googlecode.json-simple:json-simple:1.1.1")

    implementation("dev.jorel:commandapi-bukkit-shade:9.7.0")
    implementation("org.reflections:reflections:0.10.2")

    compileOnly("io.github.R2turnTrue:chzzk4j:0.0.12")
    implementation("com.github.kusaanko:YouTubeLiveChat:latest.release")
    implementation("me.taromati:afreecatvlib:1.0.3")
    implementation(
        fileTree(
            "jar/DonationAlertAPI-1.1.0.jar"
        )
    )
}

val shade = configurations.create("shade")
shade.extendsFrom(configurations.implementation.get())

kotlin {
    jvmToolchain(17)
}

tasks {
    // offline jar should be ready to go with all dependencies
    shadowJar {
        // relocate("com.github.stefvanschie.inventoryframework", "io.github.devalphagot.ppc.inventoryframework")

        // minimize()
        archiveClassifier.set("offline")
        //exclude("plugin.yml")
        //rename("offline-plugin.yml", "plugin.yml")

//        dependencies {
//            // exclude(dependency("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0"))
//            exclude(dependency("org.jetbrains.kotlin:kotlin-stdlib:1.9.20"))
//            exclude(dependency("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.9.20"))
//        }
    }

    build {
        dependsOn(shadowJar)//.dependsOn(configureShadowRelocation)
    }
}