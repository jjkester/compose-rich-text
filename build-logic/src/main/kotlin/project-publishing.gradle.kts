plugins {
    id("maven-publish")
}

publishing {
    repositories {
        maven(file(layout.buildDirectory.dir("maven"))) {
            name = "BuildFolder"
        }
    }
}
