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
import kotlin.js.json

external fun decodeURIComponent(value: String): String
external fun encodeURIComponent(value: String): String

private fun String.urlEncode() = encodeURIComponent(this)
private fun String.urlDecode() = decodeURIComponent(this)

typealias Params = Map<String, String>

private fun String.isEmptyHash() = removePrefix("#").isBlank()

private fun String.toParams(): Params {
  if (isEmptyHash()) {
    return emptyMap()
  }

  return removePrefix("?")
    .split("&")
    .map { it.split("=") }
    .associate { it[0].urlDecode() to it[1].urlDecode() }
}

private fun Params.toUrlQuery(): String {
  return if (isEmpty()) {
    ""
  } else {
    entries.joinToString(prefix = "?", separator = "&") { (key, value) ->
      "${key.urlEncode()}=${value.urlEncode()}"
    }
  }
}

private fun String.pathToHash(params: Params = emptyMap()) = "#$this${params.toUrlQuery()}"

private fun String.hashToPath(): Pair<String, Params> {
  val path = removePrefix("#")
  val url = URL("https://wardellbagby.com/${path}")

  return url.pathname.removePrefix("/") to url.search.toParams()
}

fun goTo(path: String, params: Params = emptyMap()) {
  window.location.hash = path.pathToHash(params)
}

@Composable
fun Router(
  defaultPath: String,
  defaultParams: Params = emptyMap(),
  children: @Composable (currentPath: String, params: Map<String, String>) -> Unit
) {
  var currentPath by remember { mutableStateOf(defaultPath) }
  var currentParams by remember { mutableStateOf(defaultParams) }

  LaunchedEffect(window.location.hash) {
    if (window.location.hash.isEmptyHash()) {
      console.log("Setting path to default")
      val newUrl = URL(defaultPath.pathToHash(defaultParams), base = window.location.origin)

      currentPath = defaultPath
      currentParams = defaultParams
      window.history.replaceState(null, "", newUrl.toString())
    }
  }

  DisposableEffect("hashchange") {
    val listener: (Event?) -> Unit = {
      val (path, params) = window.location.hash.hashToPath()

      currentPath = path
      currentParams = params

      console.log(
        "Updating path from hash change",
        json("newPath" to currentPath, "newParams" to currentParams.toString())
      )
    }
    window.addEventListener("hashchange", listener)
    listener(null)

    onDispose {
      window.removeEventListener("hashchange", listener)
    }
  }

  children(currentPath, currentParams)
}