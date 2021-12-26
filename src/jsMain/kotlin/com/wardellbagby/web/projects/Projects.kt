package com.wardellbagby.web.projects

import androidx.compose.runtime.Composable
import com.wardellbagby.web.LocalTextResources
import com.wardellbagby.web.getSelfImageURL

data class Project(
  val name: String,
  val url: String? = null,
  val repo: String,
  val imageUrl: String? = null,
  val shortDescription: String,
  val longDescription: String
)

@Composable
fun getProjects(): List<Project> {
  return listOf(
    Project(
      name = "Lyricistant",
      url = "https://lyricistant.app",
      repo = "https://github.com/wardellbagby/lyricistant",
      imageUrl = "https://github.com/wardellbagby/lyricistant/blob/main/packages/renderer/main/about/app_icon.png?raw=true",
      shortDescription = LocalTextResources.current["lyricistant_short_desc"],
      longDescription = LocalTextResources.current["lyricistant_long_desc"]
    ),
    Project(
      name = "Sea for ListenBrainz",
      repo = "https://github.com/wardellbagby/sea",
      imageUrl = "https://github.com/wardellbagby/sea/blob/master/app/src/main/res/mipmap-xxxhdpi/ic_launcher_round.png?raw=true",
      shortDescription = LocalTextResources.current["sea_short_desc"],
      longDescription = LocalTextResources.current["sea_long_desc"],
    ),
    Project(
      name = "wardellbagby.com",
      url = "https://wardellbagby.com",
      repo = "https://github.com/wardellbagby/wardellbagby.github.io",
      imageUrl = getSelfImageURL(),
      shortDescription = LocalTextResources.current["wardellbagby_short_desc"],
      longDescription = LocalTextResources.current["wardellbagby_long_desc"],
    ),
    Project(
      name = "GitHub Workflow Generator",
      repo = "https://github.com/wardellbagby/gh-workflow-gen",
      url = "https://www.npmjs.com/package/@wardellbagby/gh-workflow-gen",
      shortDescription = LocalTextResources.current["workflow_gen_short_desc"],
      longDescription = LocalTextResources.current["workflow_gen_long_desc"],
    ),
    Project(
      name = "RxCountDownTimer",
      repo = "https://github.com/wardellbagby/RxCountDownTimer",
      shortDescription = LocalTextResources.current["rxcdt_short_desc"],
      longDescription = LocalTextResources.current["rxcdt_long_desc"],
    )
  )
}