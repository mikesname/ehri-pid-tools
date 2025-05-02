package models

import org.apache.pekko.util.ByteString
import play.api.libs.json._
import play.api.libs.ws.{BodyWritable, InMemoryBody}

case class DoiMetadata(id: Option[String], `type`: Option[String], attributes: JsValue) {
  def state: String = (attributes \ "state").asOpt[String].getOrElse("draft")
  def prefix: String = (attributes \ "prefix").asOpt[String].getOrElse("")
  def suffix: String = (attributes \ "suffix").asOpt[String].getOrElse("")
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
