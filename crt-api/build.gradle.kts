plugins {
    `project-multiplatform-library`
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(libs.kotlinx.io)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.test.assertk)
                implementation(libs.kotlin.reflect)
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(libs.test.junit.jupiter)
                implementation(libs.test.mockito)
            }
        }
    }
}
