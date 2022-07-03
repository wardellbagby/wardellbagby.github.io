package com.wardellbagby.web.blog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.StyleBuilder
import org.jetbrains.compose.web.css.cursor
import org.jetbrains.compose.web.css.display
import org.jetbrains.compose.web.css.gridArea
import org.jetbrains.compose.web.css.gridTemplateAreas
import org.jetbrains.compose.web.css.gridTemplateColumns
import org.jetbrains.compose.web.css.gridTemplateRows
import org.jetbrains.compose.web.css.height
import org.jetbrains.compose.web.css.margin
import org.jetbrains.compose.web.css.padding
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.textAlign
import org.jetbrains.compose.web.css.width
import org.jetbrains.compose.web.dom.Article
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H4
import org.jetbrains.compose.web.dom.Img
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text

private const val imageGridArea = "Image"
private const val titleGridArea = "Title"
private const val descriptionGridArea = "Description"

@Composable
fun PostDetails(post: Post, onClick: () -> Unit) {
  val rootStyles: StyleBuilder.() -> Unit = remember {
    {
      cursor("pointer")
      gridTemplateAreas(
        *listOfNotNull(
          imageGridArea.takeUnless { post.imageUrl == null },
          titleGridArea,
          descriptionGridArea
        ).toTypedArray()
      )
      display(DisplayStyle.Grid)
      gridTemplateColumns("1fr")
      gridTemplateRows("${"minmax(auto, 250px) ".takeUnless { post.imageUrl == null }}auto auto")
      textAlign("left")
      padding(0.px)
      property("margin", "calc(var(--spacing) * 2) 0")
    }
  }
  Article({
    style(rootStyles)
    onClick {
      onClick()
    }
  }) {
    post.imageUrl?.also {
      Img(post.imageUrl) {
        style {
          width(100.percent)
          height(100.percent)
          gridArea(imageGridArea)
          property("object-fit", "cover")
        }
      }
    }
    Div({
      style {
        padding(32.px)
      }
    }) {
      H4({
        style {
          gridArea(titleGridArea)
          margin(0.px)
        }
      }) {
        Text(post.title)
      }
      Span({ style { gridArea(descriptionGridArea) } }) { Text(post.publishDate) }
    }
  }
}