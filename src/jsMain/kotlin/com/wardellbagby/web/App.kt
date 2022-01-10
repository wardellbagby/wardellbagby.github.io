package com.wardellbagby.web

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import com.wardellbagby.web.home.HomePage
import com.wardellbagby.web.intl.TextResources
import com.wardellbagby.web.layout.Footer
import com.wardellbagby.web.layout.Header
import com.wardellbagby.web.projects.ProjectPage
import com.wardellbagby.web.router.Router
import com.wardellbagby.web.router.SlideOutAnimator
import org.jetbrains.compose.web.css.paddingTop
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Main

val LocalTextResources = compositionLocalOf { TextResources() }

@Composable
fun App() {
  Header()
  Main({
    classes("container")
    style {
      paddingTop(24.px)
    }
  }) {
    Router(defaultPath = "home") { routerPath, routerParams ->
      SlideOutAnimator(routerPath to routerParams) { (path, params) ->
        when (path) {
          "home" -> HomePage()
          "project" -> ProjectPage(params)
          else -> Unknown()
        }
      }
    }
  }
  Footer()
}