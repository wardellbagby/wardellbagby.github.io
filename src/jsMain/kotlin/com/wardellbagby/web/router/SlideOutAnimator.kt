package com.wardellbagby.web.router

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.browser.window
import kotlinx.coroutines.delay
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.display
import org.jetbrains.compose.web.css.gridArea
import org.jetbrains.compose.web.css.gridTemplateAreas
import org.jetbrains.compose.web.css.gridTemplateColumns
import org.jetbrains.compose.web.css.gridTemplateRows
import org.jetbrains.compose.web.css.left
import org.jetbrains.compose.web.css.overflowX
import org.jetbrains.compose.web.css.position
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.right
import org.jetbrains.compose.web.dom.AttrBuilderContext
import org.jetbrains.compose.web.dom.Div
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.SMOOTH
import org.w3c.dom.ScrollBehavior
import org.w3c.dom.ScrollToOptions
import kotlin.js.Date
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun <T : Any> SlideOutAnimator(
  target: T,
  content: @Composable (T) -> Unit
) {
  val attrs: AttrBuilderContext<HTMLDivElement> =
    remember {
      {
        style {
          display(DisplayStyle.Grid)
          gridTemplateColumns("1fr")
          gridTemplateRows("1fr")
          gridTemplateAreas("Root")
          overflowX("hidden")
        }
      }
    }

  var previousTarget by remember { mutableStateOf<T?>(null) }
  val windowWidth = remember(target) { window.innerWidth }

  LaunchedEffect(target) {
    window.scrollTo(ScrollToOptions(top = 0.0, behavior = ScrollBehavior.SMOOTH))
  }

  if (previousTarget == null) {
    previousTarget = target
    Div(attrs = attrs) {
      content(target)
    }
    return
  }

  if (previousTarget == target) {
    Div(attrs = attrs) {
      content(target)
    }
    return
  }

  val oldContentLeft by animateInt(
    key = target,
    start = 0,
    end = windowWidth,
    duration = 300.milliseconds
  )
  val newContentRight = (windowWidth - oldContentLeft)

  if (oldContentLeft >= windowWidth) {
    previousTarget = target
  }

  Div(attrs = attrs) {
    if (oldContentLeft < windowWidth) {
      Div(
        attrs = {
          style {
            position(Position.Relative)
            gridArea("Root")
            left(oldContentLeft.px)
          }
        }
      ) {
        content(previousTarget!!)
      }
    }

    Div(
      attrs = {
        style {
          position(Position.Relative)
          gridArea("Root")
          property("z-index", 1)
          if (newContentRight > 0) {
            right(newContentRight.px)
          }
        }
      }
    ) {
      content(target)
    }
  }
}

@Composable
private fun animateInt(
  key: Any,
  start: Int,
  end: Int,
  duration: Duration,
): State<Int> {
  val state = remember(key) { mutableStateOf(start.toFloat()) }
  val intState = remember(key) { derivedStateOf { state.value.toInt() } }
  val durationMillis = remember { duration.inWholeMilliseconds.toFloat() }

  LaunchedEffect(key) {
    var date = Date.now()
    val startTime = date
    val endTime = startTime + durationMillis
    val step: Float = when {
      start > end -> ((start - end) / durationMillis) * -1
      end > start -> ((end - start) / durationMillis)
      else -> start.toFloat()
    }

    while (true) {
      date = Date.now()
      if (date >= endTime) {
        break
      }

      state.value = start + (step * (date - startTime)).toFloat()

      delay(10L)
    }

    state.value = end.toFloat()
  }

  return intState
}