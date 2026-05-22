plugins {
    id("project-multiplatform-compose-library")
}

kotlin {
    android {
        namespace = "nl.jjkester.crt.compose"
    }

    sourceSets {
        commonMain.dependencies {
            implementation(project(":crt-api"))
            implementation(project(":crt-common"))

            implementation(project.dependencies.platform(libs.compose.bom))
            implementation(libs.compose.foundation)
            implementation(libs.compose.ui.text)
        }
    }
}
