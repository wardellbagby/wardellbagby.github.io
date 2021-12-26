import org.jetbrains.compose.compose

plugins {
  kotlin("multiplatform") version "1.6.10"
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

        implementation(npm("@picocss/pico", "1.4.2"))
      }
    }
    val jsTest by getting {
      dependencies {
        implementation(kotlin("test-js"))
      }
    }
  }
}
