plugins {
    `project-library`
}

dependencies {
    api(project(":crt-api"))

    testImplementation(platform(libs.test.junit.bom))
    testImplementation(libs.test.junit.jupiter)
    testRuntimeOnly(libs.test.junit.platformLauncher)
    testImplementation(libs.test.assertk)
    testImplementation(libs.test.mockito)
}
