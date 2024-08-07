plugins {
    `project-android-library`
}

android {
    namespace = "nl.jjkester.crt.org.slf4j.android"

    defaultConfig {
        buildConfigField("String", "SLF4J_API_VERSION", "\"${libs.slf4j.api.get().version.orEmpty()}\"")
    }

    buildFeatures {
        buildConfig = true
        compose = false
    }
}

dependencies {
    api(libs.slf4j.api)

    testImplementation(libs.test.junit.jupiter)
    testImplementation(libs.test.assertk)
    testImplementation(libs.test.mockito)
}

apiValidation {
    ignoredClasses.add("${android.namespace}.BuildConfig")
}
