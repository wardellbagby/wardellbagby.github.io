package com.wardellbagby.web.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.wardellbagby.web.LocalTextResources
import com.wardellbagby.web.blog.Post
import com.wardellbagby.web.blog.PostDetails
import com.wardellbagby.web.blog.getPosts
import com.wardellbagby.web.getSelfImageURL
import com.wardellbagby.web.networking.LoadState
import com.wardellbagby.web.networking.LoadState.Failure
import com.wardellbagby.web.networking.LoadState.Loaded
import com.wardellbagby.web.networking.LoadState.NotStarted
import com.wardellbagby.web.networking.dataOrDefault
import com.wardellbagby.web.projects.ProjectDetails
import com.wardellbagby.web.projects.getProjects
import com.wardellbagby.web.projects.goToProject
import com.wardellbagby.web.router.LocalNavigator
import com.wardellbagby.web.router.Navigator
import kotlinx.browser.window
import org.jetbrains.compose.web.css.DisplayStyle.Companion.Flex
import org.jetbrains.compose.web.css.JustifyContent
import org.jetbrains.compose.web.css.borderRadius
import org.jetbrains.compose.web.css.display
import org.jetbrains.compose.web.css.justifyContent
import org.jetbrains.compose.web.css.maxWidth
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.whiteSpace
import org.jetbrains.compose.web.css.width
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H3
import org.jetbrains.compose.web.dom.Img
import org.jetbrains.compose.web.dom.Section
import org.jetbrains.compose.web.dom.Text

const val HomeRoute = "home"

fun Navigator.goHome() {
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
  val navigator = LocalNavigator.current
  val projects = getProjects()
  var postState by remember { mutableStateOf<LoadState<Post?>>(NotStarted) }

  LaunchedEffect("latest_post") {
    postState = try {
      Loaded(getPosts(count = 1).firstOrNull())
    } catch (e: Exception) {
      console.log(e.stackTraceToString())
      Failure(e.message)
    }
  }

  val latestPost = postState.dataOrDefault(default = null)

  Section {
    HeroImage()
  }
  Section({ style { whiteSpace("pre-wrap") } }) {
    Text(LocalTextResources.current["home_blurb"])
  }
  if (latestPost != null) {
    Section({ style { maxWidth(500.px) } }) {
      H3 { Text(LocalTextResources.current["latest_blog_post"]) }
      PostDetails(latestPost) {
        window.open(latestPost.url, target = "blank")
      }
    }
  }
  Section {
    H3 { Text(LocalTextResources.current["projects"]) }
    projects.forEach { project ->
      ProjectDetails(project) {
        navigator.goToProject(project)
      }
    }
  }
}