package com.wardellbagby.web.blog

import kotlinx.browser.window
import kotlinx.coroutines.await
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromDynamic
import org.w3c.dom.url.URL
import org.w3c.fetch.RequestInit
import kotlin.js.json

private val API_ENDPOINT = URL("https://dev.to/api/")
private fun URL.appendPath(path: String): URL {
  return URL(
    url = path.removePrefix("/"),
    base = toString().removeSuffix("/") + "/"
  )
}

private fun URL.appendQueryParam(key: String, value: String): URL {
  this.searchParams.append(key, value)
  return this;
}

@Serializable
data class Post(
  val title: String,
  val id: Int,
  @SerialName("readable_publish_date")
  val publishDate: String,
  val url: String
)

@OptIn(ExperimentalSerializationApi::class)
suspend fun getPosts(count: Int = 5): List<Post> {
  val response = window.fetch(
    API_ENDPOINT
      .appendPath("articles")
      // TODO adding this makes the resulting URL work in Postman and as a direct URL in Chrome, but not with fetch?
      // .appendPath("latest")
      .appendQueryParam("username", "wardellbagby")
      .appendQueryParam("per_page", count.toString())
      .toString(),
    RequestInit(method = "GET", headers = json("Accept" to "*/*"))
  )
    .await()

  if (!response.ok) {
    error("Failed to retrieve posts: ${response.statusText}")
  }

  return Json.decodeFromDynamic(response.json().await())
}