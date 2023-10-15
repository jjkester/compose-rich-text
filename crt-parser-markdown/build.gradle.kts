plugins {
    `project-library`
}

dependencies {
    api(project(":crt-api"))
    api(project(":crt-common"))
    implementation("org.commonmark:commonmark:0.19.0")

    testImplementation(libs.test.junit.jupiter)
    testImplementation(libs.test.assertk)
    testImplementation(libs.test.mockito)
    testImplementation(libs.kotlin.reflect)
}
