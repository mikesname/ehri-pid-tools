package utils.data

import org.scalatestplus.play.PlaySpec
import play.api.i18n.Lang

class DataSpec extends PlaySpec {
  "languageCodeToName" should {
    "return correct names" in {
      languageCodeToName("en")(Lang("fr")) mustBe "anglais"
      languageCodeToName("fr")(Lang("en")) mustBe "French"
    }
  }
}
