plugins {
    `project-android-library`
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.android.screenshot)
}

android {
    namespace = "nl.jjkester.crt.compose"

    @Suppress("UnstableApiUsage")
    experimentalProperties["android.experimental.enableScreenshotTest"] = true
}

dependencies {
    implementation(project(":crt-api"))
    implementation(project(":crt-common"))

    implementation(platform(libs.compose.bom))
    implementation(libs.compose.foundation)
    implementation(libs.compose.ui.text)

    screenshotTestImplementation(project(":crt-dsl"))
    screenshotTestImplementation(libs.compose.ui.tooling)
}
