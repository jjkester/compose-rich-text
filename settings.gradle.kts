pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven {
            url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        }
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }

    versionCatalogs {
        create("libs") {
            // Versions
            version("agp", "7.4.1")
            version("kotlin", "1.8.21")
            version("compose-compiler", "1.4.7")
            version("compose-bom", "2023.05.01")

            // Plugins
            plugin("android-application", "com.android.application").versionRef("agp")
            plugin("android-library", "com.android.library").versionRef("agp")
            plugin("kotlin-jvm", "org.jetbrains.kotlin.jvm").versionRef("kotlin")
            plugin("kotlin-android", "org.jetbrains.kotlin.android").versionRef("kotlin")
            plugin("kotlin-dokka", "org.jetbrains.dokka").version("1.8.10")
            plugin("kotlinx-binaryCompatibilityValidator", "org.jetbrains.kotlinx.binary-compatibility-validator").version("0.13.1")

            // Libraries
            library("compose-bom", "androidx.compose", "compose-bom").versionRef("compose-bom")
            library("compose-ui-text", "androidx.compose.ui", "ui-text").withoutVersion()
            library("compose-ui", "androidx.compose.ui", "ui").withoutVersion()
            library("compose-runtime", "androidx.compose.runtime", "runtime").withoutVersion()
            library("compose-foundation", "androidx.compose.foundation", "foundation").withoutVersion()
            library("compose-ui-testManifest", "androidx.compose.ui", "ui-test-manifest").withoutVersion()
            library("compose-ui-tooling", "androidx.compose.ui", "ui-tooling").withoutVersion()
            library("compose-ui-toolingPreview", "androidx.compose.ui", "ui-tooling-preview").withoutVersion()
            library("slf4j-api", "org.slf4j", "slf4j-api").version("2.0.7")
        }
    }
}

include(":crt-api")
include(":crt-common")
include(":crt-demo")
include(":crt-parser-markdown")
include(":crt-renderer-compose")
include(":utils:slf4j-android")
