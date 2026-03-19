import com.vanniktech.maven.publish.KotlinMultiplatform
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.androidMultiplatformLibrary)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.vanniktechMavenPublish)
}

kotlin {

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs { browser() }

    android {
        compileSdk = 36
        minSdk = 23
        namespace = "sv.lib.squircleshape"
        experimentalProperties["android.experimental.kmp.enableAndroidResources"] = true
    }

    jvm("desktop")

    listOf(
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {

        androidMain.dependencies { /* Leave empty for now. */ }
        commonMain.dependencies {
            implementation(libs.ui)
            implementation(libs.ui.util)
            implementation(libs.runtime)
            implementation(libs.foundation)
            implementation(libs.material3)
        }

        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain.get())
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }

        val desktopMain by getting {
            dependencies { /* Leave empty for now. */ }
        }

    }

}

mavenPublishing {

    coordinates(
        groupId = "com.stoyanvuchev",
        artifactId = "squircle-shape",
        version = libs.versions.squircleShape.get().toString()
    )

    pom {

        name.set("Squircle Shape")
        description.set("Squircle Shape is a Compose Multiplatform library providing customizable Squircle shapes for modern UI components.")
        inceptionYear.set("2023-2026")
        url.set("https://github.com/stoyan-vuchev/squircle-shape")

        licenses {
            license {
                name.set("MIT")
                url.set("https://opensource.org/licenses/MIT")
            }
        }

        developers {
            developer {
                id.set("stoyan-vuchev")
                name.set("Stoyan Vuchev")
                email.set("contact@stoyanvuchev.com")
                url.set("https://github.com/stoyan-vuchev")
            }
        }

        scm {
            url.set("https://github.com/stoyan-vuchev/squircle-shape")
        }

    }

    // Enable GPG signing for all publications.
    signAllPublications()

    // Configure KMP platform.
    configure(
        platform = KotlinMultiplatform(
            androidVariantsToPublish = listOf("release", "debug"),
        )
    )

}

tasks.register("testClasses") {}