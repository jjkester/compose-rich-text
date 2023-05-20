plugins {
    `project-android-library`
}

android {
    namespace = "nl.jjkester.crt.compose"
}

dependencies {
    implementation(project(":crt-api"))
    implementation(project(":crt-common"))

    implementation(libs.compose.foundation)
    implementation(libs.compose.ui.text)

    // For preview
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.toolingPreview)
    debugImplementation(libs.compose.ui.tooling)
}
