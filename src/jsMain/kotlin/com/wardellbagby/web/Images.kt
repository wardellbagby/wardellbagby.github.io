package com.wardellbagby.web

fun getSelfImageURL(size: UInt = 512.toUInt()): String {
  return "https://s.gravatar.com/avatar/25281dadfc9d94276dbd2942f94ab599?s=${size}"
}