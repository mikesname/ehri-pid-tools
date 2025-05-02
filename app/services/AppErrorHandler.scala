package services

import javax.inject._

import play.api.http._

class AppErrorHandler @Inject() (
  jsonHandler: JsonHttpErrorHandler,
  htmlHandler: DefaultHttpErrorHandler,
) extends PreferredMediaTypeHttpErrorHandler(
  "application/vnd.api+json" -> jsonHandler,
  "application/json" -> jsonHandler,
  "text/html"        -> htmlHandler,
)
