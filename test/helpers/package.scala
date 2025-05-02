import play.api.libs.json.{JsValue, Json}

package object helpers {
  def resourceAsString(name: String): String =
    getClass.getClassLoader.getResourceAsStream(name) match {
      case null => throw new IllegalArgumentException(s"Resource not found: $name")
      case stream => scala.io.Source.fromInputStream(stream).mkString
    }

  def resourceAsJson(name: String): JsValue = Json.parse(resourceAsString(name))
}
