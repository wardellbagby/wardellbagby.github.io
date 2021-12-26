package com.wardellbagby.web.router

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.browser.window
import org.w3c.dom.events.Event
import org.w3c.dom.url.URL

external fun decodeURIComponent(value: String): String
external fun encodeURIComponent(value: String): String

private fun String.urlEncode() = encodeURIComponent(this)
private fun String.urlDecode() = decodeURIComponent(this)

private fun String.toParams(): Map<String, String> {
  if (length < 2) {
    return emptyMap()
  }

  return removePrefix("?")
    .split("&")
    .map { it.split("=") }
    .associate { it[0].urlDecode() to it[1].urlDecode() }
}

private fun Map<String, String>.toQueryParams(): String {
  return if (isEmpty()) {
    ""
  } else {
    entries.joinToString(prefix = "?", separator = "&") { (key, value) ->
      "${key.urlEncode()}=${value.urlEncode()}"
    }
  }
}

fun goTo(path: String, params: Map<String, String> = emptyMap()) {
  window.location.hash = "#${path}${params.toQueryParams()}"
}

@Composable
fun Router(
  defaultPath: String,
  children: @Composable (currentPath: String, params: Map<String, String>) -> Unit
) {
  var currentPath by remember { mutableStateOf(window.location.hash) }
  var currentParams by remember { mutableStateOf(emptyMap<String, String>()) }

  LaunchedEffect(window.location.hash) {
    if (window.location.hash.length < 2) {
      console.log("Setting hash to default")
      window.location.hash = "#${defaultPath}"
    }
  }

  DisposableEffect("hashchange") {
    val listener: (Event?) -> Unit = {
      val path = window.location.hash.removePrefix("#")
      val url = URL("https://wardellbagby.com/${path}")

      currentPath = url.pathname.removePrefix("/")
      currentParams = url.search.toParams()

      console.log(currentPath, currentParams.toString())
    }
    window.addEventListener("hashchange", listener)
    listener(null)

    onDispose {
      window.removeEventListener("hashchange", listener)
    }
  }

  children(currentPath, currentParams)
}