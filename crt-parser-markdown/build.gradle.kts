plugins {
    `project-library`
}

dependencies {
    api(project(":crt-api"))
    api(project(":crt-common"))
    implementation("org.commonmark:commonmark:0.19.0")

    testImplementation(platform(libs.test.junit.bom))
    testImplementation(libs.test.junit.jupiter)
    testRuntimeOnly(libs.test.junit.platformLauncher)
    testImplementation(libs.test.assertk)
    testImplementation(libs.test.mockito)
    testImplementation(libs.kotlin.reflect)
}
