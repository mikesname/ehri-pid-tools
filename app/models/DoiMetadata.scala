package models

import org.apache.pekko.util.ByteString
import play.api.libs.json._
import play.api.libs.ws.{BodyWritable, InMemoryBody}

case class DoiMetadata(id: Option[String], `type`: Option[String], attributes: JsValue) {
  def state: String = (attributes \ "state").asOpt[String].getOrElse("draft")
  def prefix: String = id.flatMap(_.split("/").headOption).getOrElse("")
  def suffix: String = id.flatMap(_.split("/").lift(1)).getOrElse("")
  def title: Option[String] = (attributes \ "titles" \ 0 \ "title").asOpt[String]

  def withDoi(doi: String): DoiMetadata = {
    val updatedAttributes = attributes.as[JsObject] + ("doi" -> JsString(doi))
    this.copy(id = Some(doi), attributes = updatedAttributes)
  }

  def withUrl(url: String): DoiMetadata = {
    val updatedAttributes = attributes.as[JsObject] + ("url" -> JsString(url))
    this.copy(attributes = updatedAttributes)
  }

  def asDataCiteMetadata: DataCiteMetadata = attributes.as[DataCiteMetadata]
}

object DoiMetadata {
  implicit val _format: Format[DoiMetadata] = Json.format[DoiMetadata]

  implicit val _writeable: BodyWritable[DoiMetadata] = BodyWritable(
    (d: DoiMetadata) => InMemoryBody(ByteString(Json.toBytes(Json.obj("data" -> d)))),
    "application/vnd.api+json"
  )
}
