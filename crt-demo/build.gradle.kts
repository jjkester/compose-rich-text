import com.android.build.gradle.internal.tasks.factory.dependsOn

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "nl.jjkester.crt.demo"
    compileSdk = 34

    defaultConfig {
        applicationId = "nl.jjkester.crt.demo"
        versionCode = 1
        versionName = "${project.version}"

        minSdk = 24
        targetSdk = 34

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunnerArguments["disableAnalytics"] = "true"
    }

    buildFeatures {
        buildConfig = true
    }
}

kotlin {
    jvmToolchain(17)
    compilerOptions.freeCompilerArgs = listOf(
        "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api",
    )
}

dependencies {
    // App UI
    implementation(platform(libs.compose.bom))
    implementation(libs.jetpack.activity)
    implementation(libs.compose.material3)
    implementation(libs.jetpack.viewmodel)
    implementation(libs.compose.ui.toolingPreview)
    debugImplementation(libs.compose.ui.tooling)
    implementation(libs.jetpack.navigation)
    implementation(libs.compose.ui.textGoogleFonts)
    implementation(libs.compose.runtime.tracing)

    // Project dependencies
    implementation(project(":crt-api"))
    implementation(project(":crt-common"))
    implementation(project(":crt-parser-markdown"))
    implementation(project(":crt-renderer-compose"))

    implementation(project(":utils:slf4j-android"))
}

// Copy project README to resources to use in the demo app
tasks.register<Copy>("copyProjectReadmeToRawRes") {
    val sourceDir = rootProject.layout.projectDirectory
    val sourceFileName = "README.md"
    val targetDir = layout.projectDirectory.dir("src/main/res/raw")
    val targetFileName = "main_readme.md"

    val inputFile = sourceDir.file(sourceFileName)
    val outputFile = targetDir.file(targetFileName)

    from(inputFile)
    into(targetDir)
    rename(sourceFileName, targetFileName)

    inputs.file(inputFile)
    outputs.file(outputFile)
}.also { tasks.preBuild.dependsOn(it) }
