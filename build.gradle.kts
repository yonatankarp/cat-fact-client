import com.diffplug.gradle.spotless.SpotlessExtension

plugins {
    id("maven-publish")
    id("com.diffplug.spotless") version "6.25.0"
    `java-library`
    kotlin("jvm") version "1.9.23"
}

repositories {
    mavenCentral()
}

dependencies {

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")

    // Serialization
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.17.0")

    // Retrofit
    api("com.squareup.okhttp3:okhttp:4.12.0")
    api("com.squareup.retrofit2:retrofit:2.10.0")
    api("com.squareup.retrofit2:converter-jackson:2.10.0")

    // Tests
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.10.2")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.0")
    testImplementation("com.squareup.okhttp3:mockwebserver:4.12.0")
    testImplementation("io.mockk:mockk:1.13.10")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

tasks.jar {
    archiveFileName.set("${project.name}-${project.version}.jar")
    manifest {
        attributes(
            "Implementation-Title" to project.name,
            "Implementation-Version" to project.version,
        )
    }
    from(
        configurations.runtimeClasspath
            .get()
            .filter { it.exists() }
            .map { if (it.isDirectory) it else zipTree(it) },
    )
    exclude("META-INF/*.SF", "META-INF/*.DSA", "META-INF/*.RSA", "META-INF/LICENSE.txt")
    includeEmptyDirs = false
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "com.yonatankarp"
            artifactId = project.name
            version = project.version.toString()

            from(components["java"])
        }

        repositories {
            maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/yonatankarp/cat-fact-client")
                credentials {
                    username = project.findProperty("gpr.user")?.toString() ?: System.getenv("GITHUB_ACTOR")
                    password = project.findProperty("gpr.key")?.toString() ?: System.getenv("GITHUB_TOKEN")
                }
            }
        }
    }
}

configure<SpotlessExtension> {
    kotlin {
        target(
            fileTree(".") {
                include("**/*.kt")
                exclude("**/.gradle/**")
            },
        )
        // see https://github.com/shyiko/ktlint#standard-rules
        ktlint()
    }
    kotlinGradle {
        target(
            fileTree(".") {
                include("**/*.gradle.kts")
                exclude("**/.gradle/**")
            },
        )
        trimTrailingWhitespace()
        ktlint()
    }
}

tasks.findByName("spotlessKotlinGradle")?.dependsOn("spotlessKotlin")
tasks.findByName("spotlessKotlin")?.dependsOn("compileKotlin")
