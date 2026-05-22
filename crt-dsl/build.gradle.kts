plugins {
    id("project-multiplatform-library")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(project(":crt-api"))
        }
        jvmTest.dependencies {
            implementation(project.dependencies.platform(libs.test.junit.bom))
            implementation(libs.test.junit.jupiter)
            runtimeOnly(libs.test.junit.platformLauncher)
            implementation(libs.test.assertk)
            implementation(libs.test.mockito)
        }
    }
}
