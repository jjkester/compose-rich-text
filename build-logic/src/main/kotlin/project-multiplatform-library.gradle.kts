import org.jetbrains.kotlin.gradle.dsl.abi.ExperimentalAbiValidation
import org.jetbrains.kotlin.gradle.targets.jvm.tasks.KotlinJvmTest

plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("project-publishing")
    id("project-kdoc")
}

kotlin {
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
