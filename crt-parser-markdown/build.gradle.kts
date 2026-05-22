plugins {
    id("project-library")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(project(":crt-api"))
            api(project(":crt-common"))
        }
        jvmMain.dependencies {
            implementation("org.commonmark:commonmark:0.19.0")
        }
        jvmTest.dependencies {
            implementation(project.dependencies.platform(libs.test.junit.bom))
            implementation(libs.test.junit.jupiter)
            runtimeOnly(libs.test.junit.platformLauncher)
            implementation(libs.test.assertk)
            implementation(libs.test.mockito)
            implementation(libs.kotlin.reflect)
        }
    }
}
