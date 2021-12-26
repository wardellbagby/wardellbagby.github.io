package com.wardellbagby.web.home

import androidx.compose.runtime.Composable
import com.wardellbagby.web.LocalTextResources
import com.wardellbagby.web.getSelfImageURL
import com.wardellbagby.web.projects.ProjectDetails
import com.wardellbagby.web.projects.getProjects
import com.wardellbagby.web.projects.goToProjectPage
import com.wardellbagby.web.router.goTo
import org.jetbrains.compose.web.css.DisplayStyle.Companion.Flex
import org.jetbrains.compose.web.css.JustifyContent
import org.jetbrains.compose.web.css.borderRadius
import org.jetbrains.compose.web.css.display
import org.jetbrains.compose.web.css.justifyContent
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.whiteSpace
import org.jetbrains.compose.web.css.width
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H3
import org.jetbrains.compose.web.dom.Img
import org.jetbrains.compose.web.dom.Section
import org.jetbrains.compose.web.dom.Text

const val HomeRoute = "home"

fun goToHomePage() {
  goTo(HomeRoute)
}

@Composable
private fun HeroImage() {
  Div({
    style {
      display(Flex)
      justifyContent(JustifyContent.Center)
    }
  }) {
    Img(getSelfImageURL(250.toUInt())) {
      style {
        width(25.percent)
        borderRadius(50.percent)
      }
    }
  }
}

@Composable
fun HomePage() {
  val projects = getProjects()

  Section {
    HeroImage()
  }
  Section({ style { whiteSpace("pre-wrap") } }) {
    Text(LocalTextResources.current["home_blurb"])
  }
  Section {
    H3 { Text(LocalTextResources.current["projects"]) }
    projects.forEach { project ->
      ProjectDetails(project) {
        goToProjectPage(project)
      }
    }
  }
}