import gradle.kotlin.dsl.accessors._eda23fc48a14c6278f5aa91bface72ca.publishing

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
