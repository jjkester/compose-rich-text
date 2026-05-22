import org.jetbrains.kotlin.gradle.dsl.abi.ExperimentalAbiValidation

plugins {
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.android.library)
    id("project-kdoc")
    id("project-publishing")
}

android {
    compileSdk = 36
    namespace = "nl.jjkester.crt.org.slf4j.android"

    defaultConfig {
        minSdk = 24

        buildConfigField("String", "SLF4J_API_VERSION", "\"${libs.slf4j.api.get().version.orEmpty()}\"")
    }

    testOptions {
        unitTests.all {
            it.useJUnitPlatform()
        }
    }

    buildFeatures {
        buildConfig = true
        compose = false
    }

    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }
}

dependencies {
    api(libs.slf4j.api)

    testImplementation(platform(libs.test.junit.bom))
    testImplementation(libs.test.junit.jupiter)
    testRuntimeOnly(libs.test.junit.platformLauncher)
    testImplementation(libs.test.assertk)
    testImplementation(libs.test.mockito)
}

kotlin {
    jvmToolchain(17)
    @OptIn(ExperimentalAbiValidation::class)
    abiValidation {
        filters {
            exclude {
                byNames.add("${android.namespace}.BuildConfig")
            }
        }
    }
}
