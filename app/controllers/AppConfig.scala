package controllers

import play.api.Configuration

import javax.inject.Inject

case class AppConfig @Inject()(config: Configuration) {

  def doiPrefix: String =
    config.get[String]("doi.prefix")

  def doiUrl(doi: String): String =
    s"${config.get[String]("doi.resolverUrl")}/$doi"
}
