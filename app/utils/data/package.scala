package utils

import play.api.i18n.{Lang, Messages}

import java.util.Locale

package object data {
  def languageCodeToName(code: String)(implicit lang: Lang): String = {
    new Locale(code).getDisplayLanguage(lang.toLocale)
  }
}
