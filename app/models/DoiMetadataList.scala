package models

import play.api.libs.json._
import play.api.libs.functional.syntax._

case class DoiMetadataList(dois: Seq[DoiMetadata], total: Int, page: Int, totalPages: Int)

object DoiMetadataList {
  implicit def _reads: Reads[DoiMetadataList] = (
    (__ \ "data").read[Seq[DoiMetadata]] and
    (__ \ "meta" \ "total").read[Int] and
    (__ \ "meta" \ "page").read[Int] and
    (__ \ "meta" \ "totalPages").read[Int]
  )(DoiMetadataList.apply _)
}
