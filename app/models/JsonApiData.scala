package models

import play.api.libs.functional.syntax._
import play.api.libs.json._

/**
 * A wrapper for the JSON+API structure returned
 * from DataCite APIs.
 */
case class JsonApiData(data: JsValue, meta: Option[JsValue] = None, links: Option[JsValue] = None)
object JsonApiData {
  implicit val _writes: Writes[JsonApiData] = (
    (__ \ "data").write[JsValue] and
    (__ \ "meta").writeNullable[JsValue] and
    (__ \ "links").writeNullable[JsValue]
  )(unlift(JsonApiData.unapply))

  implicit val _reads: Reads[JsonApiData] = Json.reads[JsonApiData]
}
