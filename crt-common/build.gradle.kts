plugins {
    `project-library`
}

dependencies {
    api(project(":crt-api"))
    api(libs.slf4j.api)

    testImplementation(libs.test.junit.jupiter)
    testImplementation(libs.test.assertk)
    testImplementation(libs.test.mockito)
}
