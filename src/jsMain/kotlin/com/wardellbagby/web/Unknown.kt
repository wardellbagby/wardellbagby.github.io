package com.wardellbagby.web

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.wardellbagby.web.DuckLoadState.Failure
import com.wardellbagby.web.DuckLoadState.Loaded
import com.wardellbagby.web.DuckLoadState.Loading
import com.wardellbagby.web.DuckLoadState.NotStarted
import kotlinx.browser.window
import kotlinx.coroutines.await
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
import kotlin.js.Json

private sealed class DuckLoadState {
  object NotStarted : DuckLoadState()
  object Loading : DuckLoadState()
  data class Loaded(val url: String) : DuckLoadState()
  object Failure : DuckLoadState()
}

private const val imageEndpoint = "https://random.dog/woof.json?filter=mp4,webm"

@Composable
fun Unknown() {
  var duckState: DuckLoadState by remember { mutableStateOf(NotStarted) }
  Section { Text(LocalTextResources.current["unknown_blurb"]) }

  LaunchedEffect("duck") {
    duckState = Loading

    duckState = try {
      val result = window.fetch(imageEndpoint).await()

      if (result.ok) {
        val response = result.json().await().unsafeCast<Json>()
        val url = response["url"]
        if (url != null && url is String) {
          Loaded(url)
        } else {
          Failure
        }
      } else {
        Failure
      }
    } catch (e: Throwable) {
      console.error(e)
      Failure
    }
  }

  Section({
    style {
      display(DisplayStyle.Flex)
      justifyContent(JustifyContent.Center)
    }
  }) {
    when (val state = duckState) {
      Failure -> Text(LocalTextResources.current["unknown_load_failure"])
      Loading, NotStarted -> Img("") {
        attr("aria-busy", "true")
      }
      is Loaded -> Img(state.url) {
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