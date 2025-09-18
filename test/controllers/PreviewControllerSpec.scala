package controllers

import helpers.AppSpec
import play.api.test.Helpers._
import play.api.test._

/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 *
 * For more information, see https://www.playframework.com/documentation/latest/ScalaTestingWithScalaTest
 */
class PreviewControllerSpec extends AppSpec {

  private def controller = inject[PreviewController]
  private val testUrl = "https://example.com/preview-test"
  private val testUrl2 = "https://example.com/preview-test2"

  "PreviewController GET" should {

    "return an HTML snippet for a given URL" in {
      val request = FakeRequest(GET, routes.PreviewController.preview(testUrl).url)
      val result = controller.preview(testUrl).apply(request)
      status(result) mustBe OK
      contentType(result) mustBe Some("text/html")

      val html = contentAsString(result)
      html must include ("Preview Generator Test Page | Nice Site")
      html must include ("https://example.com/preview-test-image.svg")
    }

    "deal with missing images" in {
      val request = FakeRequest(GET, routes.PreviewController.preview(testUrl2).url)
      val result = controller.preview(testUrl2).apply(request)
      status(result) mustBe OK
      contentType(result) mustBe Some("text/html")

      val html = contentAsString(result)
      html must not include ("https://example.com/preview-test-image2.svg")

    }
  }
}
