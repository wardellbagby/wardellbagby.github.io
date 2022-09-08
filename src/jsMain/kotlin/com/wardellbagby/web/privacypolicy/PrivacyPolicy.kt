package com.wardellbagby.web.privacypolicy

import androidx.compose.runtime.Composable
import com.wardellbagby.web.HGroup
import com.wardellbagby.web.Unknown
import com.wardellbagby.web.projects.Project
import com.wardellbagby.web.projects.getProjects
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.FlexDirection
import org.jetbrains.compose.web.css.alignItems
import org.jetbrains.compose.web.css.display
import org.jetbrains.compose.web.css.flexDirection
import org.jetbrains.compose.web.css.keywords.auto
import org.jetbrains.compose.web.css.padding
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.textAlign
import org.jetbrains.compose.web.css.whiteSpace
import org.jetbrains.compose.web.css.width
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H3
import org.jetbrains.compose.web.dom.Img
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text

const val PrivacyPolicyRoute = "project"

@Composable
fun PrivacyPolicy(params: Map<String, Any>) {
  val project = params["name"]?.let { name -> getProjects().firstOrNull { it.name == name } }

  if (project == null) {
    Unknown()
  } else {
    PrivacyPolicy(project)
  }
}

@Composable
private fun PrivacyPolicy(project: Project) {
  Div({
    style {
      display(DisplayStyle.Flex)
      flexDirection(FlexDirection.Column)
      alignItems(AlignItems.Center)
    }
  }) {
    project.imageUrl?.let {
      Img(it) {
        style {
          width(128.px)
          padding(16.px)
          property("margin", auto)
        }
      }
    }
    HGroup({ style { textAlign("center") } }) {
      H3 {
        Text(project.name)
      }
    }
    P({ style { whiteSpace("pre-wrap") } }) {
      Text(
        project.privacyPolicy ?: "No privacy policy available."
      )
    }
  }
}