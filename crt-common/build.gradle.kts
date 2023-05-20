plugins {
    `project-library`
}

dependencies {
    api(project(":crt-api"))
    api(libs.slf4j.api)
}
