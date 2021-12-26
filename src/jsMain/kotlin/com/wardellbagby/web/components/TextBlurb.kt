package com.wardellbagby.web.components

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.dom.Article
import org.jetbrains.compose.web.dom.H3
import org.jetbrains.compose.web.dom.Text

@Composable
fun TextBlurb(
  title: String? = null,
  blurb: String
) {
  Article {
    title?.also {
      H3 {
        Text(title)
      }
    }
    Text(blurb)
  }
}