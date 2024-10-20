plugins {
    `project-multiplatform-library`
}

kotlin {
    val commonMain by sourceSets.getting {
        dependencies {
            api(project(":crt-api"))
        }
    }
    val commonTest by sourceSets.getting {
        dependencies {
            implementation(libs.test.assertk)
        }
    }
    val jvmMain by sourceSets.getting {
        dependencies {
            api(libs.slf4j.api)
        }
    }
    val jvmTest by sourceSets.getting {
        dependencies {
            implementation(libs.test.junit.jupiter)
            implementation(libs.test.mockito)
        }
    }
}
