pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "multilayout-video"

include(
    ":app",
    ":feature-picker",
    ":feature-editor",
    ":feature-export",
    ":core-media",
    ":core-playback",
    ":core-timeline",
    ":core-layout",
    ":core-audio",
    ":data-project",
    ":domain",
)
