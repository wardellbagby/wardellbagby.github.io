import org.jetbrains.compose.compose
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension

plugins {
  kotlin("multiplatform") version "1.6.10"
  kotlin("plugin.serialization") version "1.6.10"
  id("org.jetbrains.compose") version "1.0.1"
}

group = "com.wardellbagby"
version = "1.0"

repositories {
  google()
  mavenCentral()
  maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

kotlin {
  js(IR) {
    browser {
      commonWebpackConfig {
        cssSupport.enabled = true
        outputFileName = "index.js"
      }
      testTask {
        testLogging.showStandardStreams = true
        useKarma {
          useChromeHeadless()
          useFirefox()
        }
      }
    }
    binaries.executable()
  }
  sourceSets {
    val jsMain by getting {
      dependencies {
        implementation(compose.web.core)
        implementation(compose.runtime)
        implementation("org.jetbrains.kotlin-wrappers:kotlin-extensions:1.0.1-pre.284-kotlin-1.6.10")
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")

        implementation(npm("@picocss/pico", "1.5.3"))
      }
    }
    val jsTest by getting {
      dependencies {
        implementation(kotlin("test-js"))
      }
    }
  }
}

// https://stackoverflow.com/questions/72731436/kotlin-multiplatform-gradle-task-jsrun-gives-error-webpack-cli-typeerror-c/72731728
rootProject.extensions.configure<NodeJsRootExtension> {
  versions.webpackCli.version = "4.10.0"
}