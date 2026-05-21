import org.jetbrains.kotlin.gradle.dsl.abi.ExperimentalAbiValidation

plugins {
    `project-android-library`
}

android {
    namespace = "nl.jjkester.crt.org.slf4j.android"

    defaultConfig {
        buildConfigField("String", "SLF4J_API_VERSION", "\"${libs.slf4j.api.get().version.orEmpty()}\"")
    }

    buildFeatures {
        buildConfig = true
        compose = false
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
    @OptIn(ExperimentalAbiValidation::class)
    abiValidation {
        filters {
            exclude {
                byNames.add("${android.namespace}.BuildConfig")
            }
        }
    }
}
