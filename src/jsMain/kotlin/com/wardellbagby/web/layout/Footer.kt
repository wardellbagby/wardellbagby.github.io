package com.wardellbagby.web.layout

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.attributes.ATarget.Blank
import org.jetbrains.compose.web.attributes.target
import org.jetbrains.compose.web.css.textAlign
import org.jetbrains.compose.web.dom.A
import org.jetbrains.compose.web.dom.Aside
import org.jetbrains.compose.web.dom.Li
import org.jetbrains.compose.web.dom.Nav
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.dom.Ul
import org.jetbrains.compose.web.dom.Footer as FooterElement

@Composable
fun Footer() {
  FooterElement({
    classes("container")
    style {
      textAlign("center")
      property("border-top", "1px solid var(--card-background-color)")
    }
  }) {
    Aside {
      Nav {
        Ul {
          Li {
            A(href = "https://twitter.com/wardellbagby", { target(Blank) }) {
              Text("Twitter")
            }
          }
          Li {
            A(href = "https://github.com/wardellbagby", { target(Blank) }) {
              Text("GitHub")
            }
          }
          Li {
            A(href = "https://linkedin.com/in/wardellbagby/", { target(Blank) }) {
              Text("LinkedIn")
            }
          }
        }
      }
    }
  }
}