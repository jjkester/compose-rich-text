plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(libs.plugin.kotlin)
    implementation(libs.plugin.dokka)
    implementation(libs.plugin.compose)
    implementation(libs.plugin.kotlin.compose)
    implementation(libs.plugin.android)
}
