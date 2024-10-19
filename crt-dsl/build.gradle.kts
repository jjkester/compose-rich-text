plugins {
    `project-library`
}

dependencies {
    api(project(":crt-api"))

    testImplementation(libs.test.junit.jupiter)
    testImplementation(libs.test.assertk)
    testImplementation(libs.test.mockito)
}
