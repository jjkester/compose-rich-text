plugins {
    `project-library`
}

dependencies {
    api(project(":crt-api"))
    api(project(":crt-common"))
    implementation("org.commonmark:commonmark:0.19.0")
}
