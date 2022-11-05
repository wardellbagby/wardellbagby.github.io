package com.wardellbagby.web.router

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.referentialEqualityPolicy
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.browser.document
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

private fun String.pathToHash(params: Params = emptyMap()): String {
  val prefix = if (this.isBlank()) "" else "#$this"
  val queryParams = params.toUrlQuery()
  return "$prefix$queryParams"
}

private fun String.hashToPath(): Pair<String, Params> {
  val path = removePrefix("#")
  val url = URL("https://wardellbagby.com/${path}")

  return url.pathname.removePrefix("/") to url.search.toParams()
}

fun interface Navigator {
  fun goTo(path: String, params: Map<String, String>)
  fun goTo(path: String) = goTo(path, emptyMap())
}

val LocalNavigator = compositionLocalOf(policy = referentialEqualityPolicy()) {
  Navigator { _, _ -> error("Navigation is not currently available") }
}

@Composable
fun Router(
  defaultPath: String,
  defaultParams: Params = emptyMap(),
  children: @Composable (currentPath: String, params: Map<String, String>) -> Unit
) {
  var currentPath by remember { mutableStateOf(defaultPath) }
  var currentParams by remember { mutableStateOf(defaultParams) }

  val navigator: Navigator = remember(defaultPath) {
    Navigator { path: String, params: Map<String, String> ->
      if (path == defaultPath) {
        // We still update the hash to give us an actual update but...
        window.location.hash = ""
        // We use replaceState here since an empty hash always adds a "#" symbol.
        window.history.replaceState(
          "",
          document.title,
          "${window.location.pathname}${params.toUrlQuery()}"
        )
      } else {
        window.location.hash = path.pathToHash(params)
      }

    }
  }

  DisposableEffect("hashchange") {
    val listener: (Event?) -> Unit = {
      val (path, params) = window.location.hash.hashToPath()

      when {
        path.isBlank() -> {
          currentPath = defaultPath
          currentParams = params
        }

        else -> {
          currentPath = path
          currentParams = params
        }
      }

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

  CompositionLocalProvider(LocalNavigator provides navigator) {
    children(currentPath, currentParams)
  }
}