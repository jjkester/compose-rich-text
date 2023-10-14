plugins {
    `project-library`
}

dependencies {
    testImplementation(libs.test.junit.jupiter)
    testImplementation(libs.test.assertk)
    testImplementation(libs.test.mockito)
    testImplementation(libs.kotlin.reflect)
}
