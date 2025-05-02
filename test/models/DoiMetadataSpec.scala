package models

import helpers.resourceAsJson
import org.scalatestplus.play.PlaySpec

class DoiMetadataSpec extends PlaySpec {

  "DoiMetadata" should {
    "correctly parse the id, prefix, and suffix" in {
      val json = resourceAsJson("example.json")
      val doiMetadata = json.as[JsonApiData].data.as[DoiMetadata]
      doiMetadata.id mustBe Some("10.14454/fxws-0523")
    }
  }

  "correctly parse doi metadata" in {
    val json = resourceAsJson("example.json")
    val doiMetadata = json.as[JsonApiData].data.as[DoiMetadata]
    doiMetadata.attributes.asOpt[DataCiteMetadata].isDefined mustBe true
  }
}
