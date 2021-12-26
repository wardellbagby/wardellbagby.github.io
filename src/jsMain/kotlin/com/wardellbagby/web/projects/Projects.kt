import androidx.compose.runtime.Composable
import com.wardellbagby.web.LocalTextResources

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
      repo = "",
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
    )
  )
}