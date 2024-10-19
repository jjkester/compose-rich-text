import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootPlugin
import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnPlugin
import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnRootExtension

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.kotlin.dokka) apply false
    alias(libs.plugins.kotlinx.binaryCompatibilityValidator) apply false
}

// Disable auto-adding of JS repositories
rootProject.plugins.withType<NodeJsRootPlugin> {
    rootProject.the<NodeJsRootExtension>().downloadBaseUrl = null
}
rootProject.plugins.withType<YarnPlugin> {
    rootProject.the<YarnRootExtension>().downloadBaseUrl = null
}

allprojects {
    group = "nl.jjkester.crt"
    version = "0.1.0-SNAPSHOT"
}
