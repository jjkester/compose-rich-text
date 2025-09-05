plugins {
    `project-multiplatform-library`
}

kotlin {
    val commonMain by sourceSets.getting {
        dependencies {
            api(project(":crt-api"))
            api(project(":crt-common"))
        }
    }
    val commonTest by sourceSets.getting {
        dependencies {
            implementation(libs.test.assertk)
            implementation(libs.kotlin.reflect)
        }
    }
    val jvmMain by sourceSets.getting {
        dependencies {
            implementation("org.commonmark:commonmark:0.19.0")
        }
    }
    val jvmTest by sourceSets.getting {
        dependencies {
            implementation(libs.test.junit.jupiter)
            implementation(libs.test.mockito)
        }
    }
}
