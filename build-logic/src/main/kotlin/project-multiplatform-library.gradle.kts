import org.jetbrains.kotlin.gradle.dsl.abi.ExperimentalAbiValidation
import org.jetbrains.kotlin.gradle.targets.jvm.tasks.KotlinJvmTest

plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("project-publishing")
    id("project-kdoc")
}

kotlin {
    // Targets
    jvm()
    iosArm64()
    iosSimulatorArm64()

    // Kotlin settings
    explicitApi()

    @OptIn(ExperimentalAbiValidation::class)
    abiValidation {
        enabled.set(true)
    }

    jvmToolchain(17)
}

tasks.withType<KotlinJvmTest>().configureEach {
    useJUnitPlatform()
}
