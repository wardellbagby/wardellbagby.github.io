package com.wardellbagby.web.layout

import androidx.compose.runtime.Composable
import com.wardellbagby.web.HGroup
import com.wardellbagby.web.LocalTextResources
import com.wardellbagby.web.home.goHome
import com.wardellbagby.web.router.LocalNavigator
import kotlinx.browser.window
import org.jetbrains.compose.web.css.flexGrow
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.width
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H2
import org.jetbrains.compose.web.dom.H3
import org.jetbrains.compose.web.dom.Li
import org.jetbrains.compose.web.dom.Nav
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.dom.Ul
import org.jetbrains.compose.web.dom.Header as HeaderElement

@Composable
fun Header() {
  val navigator = LocalNavigator.current
  HeaderElement({
    classes("container")
    style {
      property("border-bottom", "1px solid var(--card-background-color)")
    }
  }) {
    Nav {
      Ul({ style { width(100.percent) } }) {
        Li {
          HGroup {
            H2 {
              Text(LocalTextResources.current["header_title"])
            }
            H3 {
              Text(LocalTextResources.current["header_subtitle"])
            }
          }
        }

        Div({ style { flexGrow(1) } })

        Li {
          Button({
            classes("secondary")
            onClick { navigator.goHome() }
          }) { Text(LocalTextResources.current["home"]) }
        }

        Li {
          Button({
            classes("secondary")
            onClick { window.open("https://dev.to/wardellbagby", target = "blank") }
          }) { Text(LocalTextResources.current["blog"]) }
        }
      }
    }
  }
}