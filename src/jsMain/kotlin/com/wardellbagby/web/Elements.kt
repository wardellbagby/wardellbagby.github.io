package com.wardellbagby.web

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.ExperimentalComposeWebApi
import org.jetbrains.compose.web.attributes.AttrsBuilder
import org.jetbrains.compose.web.dom.ElementScope
import org.jetbrains.compose.web.dom.TagElement
import org.w3c.dom.Element

@OptIn(ExperimentalComposeWebApi::class)
@Composable
fun HGroup(
  applyAttrs: AttrsBuilder<Element>.() -> Unit = {},
  content: (@Composable ElementScope<Element>.() -> Unit)? = {}
) = TagElement(tagName = "hgroup", applyAttrs = applyAttrs, content = content)