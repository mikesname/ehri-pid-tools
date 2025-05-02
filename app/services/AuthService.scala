package services

import com.google.inject.ImplementedBy

import scala.concurrent.Future

@ImplementedBy(classOf[ConfigAuthService])
trait AuthService {
  def authenticate(token: String): Future[Option[String]]
}
