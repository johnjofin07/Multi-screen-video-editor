plugins {
    alias(libs.plugins.kotlin.jvm)
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.javax.inject)
    testImplementation(libs.junit)
    testImplementation(libs.truth)
}
