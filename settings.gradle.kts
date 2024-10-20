pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven {
            url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        }
    }
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    // Only allow repositories defined here
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)

    // Ensure no Maven repositories are queried for non-Maven artifacts
    repositories.all {
        if (this is MavenArtifactRepository) {
            content {
                excludeGroup("org.nodejs")
                excludeModule("com.yarnpkg", "yarn")
            }
        }
    }

    repositories {
        google()
        mavenCentral()

        // JS repositories
        exclusiveContent {
            forRepository {
                ivy("https://nodejs.org/dist/") {
                    name = "Node Distributions at $url"
                    patternLayout { artifact("v[revision]/[artifact](-v[revision]-[classifier]).[ext]") }
                    metadataSources { artifact() }
                    content { includeModule("org.nodejs", "node") }
                }
            }
            filter { includeGroup("org.nodejs") }
        }
        exclusiveContent {
            forRepository {
                ivy("https://github.com/yarnpkg/yarn/releases/download") {
                    name = "Yarn Distributions at $url"
                    patternLayout { artifact("v[revision]/[artifact](-v[revision]).[ext]") }
                    metadataSources { artifact() }
                    content { includeModule("com.yarnpkg", "yarn") }
                }
            }
            filter { includeGroup("com.yarnpkg") }
        }
    }
}

include(":crt-api")
include(":crt-common")
include(":crt-demo")
include(":crt-dsl")
include(":crt-parser-markdown")
include(":crt-renderer-compose")
include(":utils:slf4j-android")
