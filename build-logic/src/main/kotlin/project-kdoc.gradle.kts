plugins {
    id("org.jetbrains.dokka")
}

val dokkaHtmlJar by tasks.registering(Jar::class) {
    description = "A HTML Documentation JAR containing Dokka HTML"
    from(tasks.dokkaGeneratePublicationHtml.flatMap { it.outputDirectory })
    archiveClassifier.set("javadoc")
}

pluginManager.withPlugin("maven-publish") {
    the<PublishingExtension>().apply {
        publications.withType<MavenPublication>().configureEach {
            artifact(dokkaHtmlJar)
        }
    }
}
