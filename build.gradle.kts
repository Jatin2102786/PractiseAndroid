// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
//    id("org.jetbrains.kotlin.android") version "2.0.0"
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.0"
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    id("com.google.gms.google-services") version "4.4.2" apply false
}