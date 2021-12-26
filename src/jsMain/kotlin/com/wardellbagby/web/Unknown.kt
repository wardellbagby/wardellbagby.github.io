package com.wardellbagby.web

import androidx.compose.runtime.Composable
import com.wardellbagby.web.components.TextBlurb

@Composable
fun Unknown() {
  TextBlurb(blurb = LocalTextResources.current["unknown_blurb"])
}