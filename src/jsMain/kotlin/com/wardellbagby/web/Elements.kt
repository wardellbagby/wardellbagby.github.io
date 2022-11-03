package com.wardellbagby.web

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.attributes.AttrsScope
import org.jetbrains.compose.web.dom.ElementScope
import org.jetbrains.compose.web.dom.TagElement
import org.w3c.dom.Element

@Composable
fun HGroup(
  applyAttrs: (AttrsScope<Element>.() -> Unit)? = {},
  content: (@Composable ElementScope<Element>.() -> Unit)?,
) = TagElement(tagName = "hgroup", applyAttrs = applyAttrs, content = content)
