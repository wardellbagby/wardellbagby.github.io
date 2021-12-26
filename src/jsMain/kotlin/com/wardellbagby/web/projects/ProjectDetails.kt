package com.wardellbagby.web.projects

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.StyleBuilder
import org.jetbrains.compose.web.css.display
import org.jetbrains.compose.web.css.gap
import org.jetbrains.compose.web.css.gridArea
import org.jetbrains.compose.web.css.gridTemplateAreas
import org.jetbrains.compose.web.css.gridTemplateColumns
import org.jetbrains.compose.web.css.gridTemplateRows
import org.jetbrains.compose.web.css.height
import org.jetbrains.compose.web.css.keywords.auto
import org.jetbrains.compose.web.css.margin
import org.jetbrains.compose.web.css.padding
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
private const val clickIconGridArea = "Icon"

@Composable
fun ProjectDetails(project: Project, onClick: () -> Unit) {
  val rootStyles: StyleBuilder.() -> Unit = remember {
    {
      gridTemplateAreas(
        "$imageGridArea $titleGridArea        $clickIconGridArea",
        "$imageGridArea $descriptionGridArea  $clickIconGridArea"
      )
      display(DisplayStyle.Grid)
      gridTemplateColumns("72px 1fr")
      gridTemplateRows("auto auto")
      gap(rowGap = 0.px, columnGap = 16.px)
      padding(32.px)
      textAlign("left")
      property("margin", "calc(var(--spacing) * 2) 0")
    }
  }
  val iconStyles: StyleBuilder.() -> Unit = remember {
    {
      gridArea(clickIconGridArea)
      width(0.px)
      height(0.px)
      property("border-top", "16px solid transparent")
      property("border-left", "8px solid var(--color)")
      property("border-bottom", "16px solid transparent")
      property("margin", auto)
    }
  }
  Article({
    style(rootStyles)
    onClick {
      onClick()
    }
  }) {
    project.imageUrl?.let {
      Img(it) {
        style {
          gridArea(imageGridArea)
          padding(8.px)
        }
      }
    }
    H4({
      style {
        gridArea(titleGridArea)
        margin(0.px)
      }
    }) {
      Text(project.name)
    }
    Span({ style { gridArea(descriptionGridArea) } }) { Text(project.shortDescription) }

    Div({ style(iconStyles) })
  }
}