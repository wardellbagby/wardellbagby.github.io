package com.wardellbagby.web

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.wardellbagby.web.networking.LoadState
import com.wardellbagby.web.networking.LoadState.Failure
import com.wardellbagby.web.networking.LoadState.Loaded
import com.wardellbagby.web.networking.LoadState.Loading
import com.wardellbagby.web.networking.LoadState.NotStarted
import kotlinx.browser.window
import kotlinx.coroutines.await
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromDynamic
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.JustifyContent
import org.jetbrains.compose.web.css.display
import org.jetbrains.compose.web.css.justifyContent
import org.jetbrains.compose.web.css.maxHeight
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.A
import org.jetbrains.compose.web.dom.Img
import org.jetbrains.compose.web.dom.Section
import org.jetbrains.compose.web.dom.Text

private const val imageEndpoint = "https://random.dog/woof.json?filter=mp4,webm"

@Serializable
data class DuckResponse(val url: String?)

@OptIn(ExperimentalSerializationApi::class)
@Composable
fun Unknown() {
  var imageUrl: LoadState<String> by remember { mutableStateOf(NotStarted) }
  Section { Text(LocalTextResources.current["unknown_blurb"]) }

  LaunchedEffect("duck") {
    imageUrl = Loading

    val result = window.fetch(imageEndpoint).await()

    imageUrl = if (result.ok) {
      val url = Json.decodeFromDynamic<DuckResponse>(result.json().await()).url

      if (url != null) {
        Loaded(url)
      } else {
        Failure("No image url sent!")
      }
    } else {
      Failure("Failed to get duck image response: ${result.statusText}")
    }
  }

  LaunchedEffect(imageUrl) {
    val state = imageUrl
    if (state is Failure) {
      console.error(state.reason)
    }
  }

  Section({
    style {
      display(DisplayStyle.Flex)
      justifyContent(JustifyContent.Center)
    }
  }) {
    when (val state = imageUrl) {
      is Failure -> Text(LocalTextResources.current["unknown_load_failure"])
      Loading, NotStarted -> Img("") {
        attr("aria-busy", "true")
      }
      is Loaded -> Img(state.data) {
        style {
          maxHeight(400.px)
        }
      }
    }
  }

  A("https://wardellbagby.com") {
    Text(LocalTextResources.current["take_me_home"])
  }
}