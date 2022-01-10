package com.wardellbagby.web.networking

import com.wardellbagby.web.networking.LoadState.Loaded

sealed class LoadState<out T> {
  object NotStarted : LoadState<Nothing>()
  object Loading : LoadState<Nothing>()
  data class Loaded<T>(val data: T) : LoadState<T>()
  data class Failure(val reason: String? = null) : LoadState<Nothing>()
}

fun <T> LoadState<T>.dataOrDefault(default: T): T {
  return if (this is Loaded) {
    data
  } else {
    default
  }
}