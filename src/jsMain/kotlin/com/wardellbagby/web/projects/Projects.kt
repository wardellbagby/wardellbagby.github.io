package com.wardellbagby.web.projects

import androidx.compose.runtime.Composable
import com.wardellbagby.web.LocalTextResources
import com.wardellbagby.web.getSelfImageURL
import kotlinext.js.require

data class Project(
  val name: String,
  val url: String? = null,
  val repo: String,
  val imageUrl: String? = null,
  val shortDescription: String,
  val longDescription: String,
  val privacyPolicy: String? = null
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
      name = "Deltas",
      repo = "https://github.com/wardellbagby/deltas",
      imageUrl = "https://raw.githubusercontent.com/wardellbagby/deltas/main/app/src/main/res/mipmap-xxxhdpi/ic_launcher.png",
      shortDescription = LocalTextResources.current["deltas_short_desc"],
      longDescription = LocalTextResources.current["deltas_long_desc"],
      privacyPolicy = LocalTextResources.current["deltas_privacy_policy"]
    ),
    Project(
      name = "@wardell_listens",
      url = "https://twitter.com/wardell_listens",
      repo = "https://github.com/wardellbagby/wardell_listens",
      imageUrl = require("./wardell_listens.png") as? String,
      shortDescription = LocalTextResources.current["wardell_listens_short_desc"],
      longDescription = LocalTextResources.current["wardell_listens_long_desc"],
    ),
    Project(
      name = "Sensor Disabler",
      repo = "https://github.com/wardellbagby/sensor-disabler",
      imageUrl = "https://raw.githubusercontent.com/wardellbagby/sensor-disabler/main/app/src/main/res/mipmap-xxxhdpi/ic_launcher.png",
      shortDescription = LocalTextResources.current["sensor_disabler_short_desc"],
      longDescription = LocalTextResources.current["sensor_disabler_long_desc"]
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
      name = "Sea for ListenBrainz",
      repo = "https://github.com/wardellbagby/sea",
      imageUrl = "https://github.com/wardellbagby/sea/blob/master/app/src/main/res/mipmap-xxxhdpi/ic_launcher_round.png?raw=true",
      shortDescription = LocalTextResources.current["sea_short_desc"],
      longDescription = LocalTextResources.current["sea_long_desc"],
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