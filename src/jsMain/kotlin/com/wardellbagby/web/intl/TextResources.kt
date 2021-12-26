package com.wardellbagby.web.intl

import kotlinext.js.getOwnPropertyNames
import kotlinext.js.require
import kotlin.js.Json

class TextResources {
  private val map = require("./strings.json").unsafeCast<Json>()

  operator fun get(key: String): String {
    if (key in map.getOwnPropertyNames()) {
      return when (val value = map[key]) {
        is Array<*> -> value.joinToString(separator = "\n\n")
        is String -> value
        null -> error("Invalid resource: $key with value null")
        else -> error("Invalid resource: $key with value $value and type ${value::class.simpleName}")
      }
    }
    error("No such resource: $key")
  }
}