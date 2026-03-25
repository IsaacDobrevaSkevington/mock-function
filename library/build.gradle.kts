import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.net.URI

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    id("maven-publish")
    alias(libs.plugins.vanniktech.maven.publish)
    alias(libs.plugins.dokka)
    signing
}

kotlin {
    withSourcesJar(publish = true)

    listOf(
        iosArm64(),
        iosSimulatorArm64(),
    )

    jvm()

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
        binaries.executable()
    }

    sourceSets {
        commonMain.dependencies {
            implementation(kotlin("test"))
        }
        commonTest.dependencies {
            implementation(kotlin("test"))
        }
    }
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = URI("https://maven.pkg.github.com/IsaacDobrevaSkevington/mock-function")
            credentials {
                username = System.getenv("USERNAME")
                password = System.getenv("TOKEN")
            }
        }
    }
}

dokka {
    moduleName.set("Mock Function")
    dokkaPublications.html {
        suppressInheritedMembers.set(true)
        failOnWarning.set(true)
        outputDirectory.set(File("${rootProject.projectDir}/docs/dokka"))
    }
    dokkaSourceSets.commonMain {
        samples.from("${rootProject.projectDir}/library/src/commonMain/kotlin/com/idscodelabs/mock_function/examples")
    }
}
signing {
    val signingKey: String? by project
    val signingPassword: String? by project
    useInMemoryPgpKeys(
        signingKey ?: System.getenv("GPG_KEY"),
        signingPassword ?: System.getenv("GPG_PASSWORD"),
    )
}

mavenPublishing {
    coordinates(
        groupId = "io.github.idscodelabs",
        artifactId = "mock-function",
        version = version.toString(),
    )

    pom {
        name.set("Mock Function")
        description.set("Multiplatform Test Library for Mocking Function Objects")
        inceptionYear.set("2025")
        url.set("https://github.com/IsaacDobrevaSkevington/mock-function")

        licenses {
            license {
                name.set("MIT")
                url.set("https://opensource.org/licenses/MIT")
            }
        }

        developers {
            developer {
                id.set("Isaac-Dobreva-Skevington")
                name.set("Isaac-Dobreva-Skevington")
                email.set("-")
            }
        }

        scm {
            url.set("https://github.com/IsaacDobrevaSkevington/mock-function")
        }
    }

    // Configure publishing to Maven Central
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)

    // Enable GPG signing for all publications
    signAllPublications()
}

