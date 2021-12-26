import com.wardellbagby.web.App
import kotlinext.js.require
import org.jetbrains.compose.web.renderComposable

fun main() {
  require("@picocss/pico/css/pico.min.css")
  require("./index.css")

  renderComposable(rootElementId = "root", content = { App() })
}

