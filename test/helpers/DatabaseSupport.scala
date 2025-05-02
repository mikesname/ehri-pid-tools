package helpers

import org.scalatest.{BeforeAndAfterEach, Suite}
import play.api.db.evolutions.Evolutions
import play.api.db.{Database, Databases}
import play.api.{Configuration, Environment, LoggerConfigurator}

trait DatabaseSupport extends BeforeAndAfterEach {
  self: Suite =>

  private def dbConfig: (String, String, Map[String, _]) = {
    // App is null at this point so we have to init the database
    // manually for the fixtures... annoying
    val env = Environment.simple()
    val config = Configuration.load(env)
    LoggerConfigurator(env.classLoader).foreach(_.configure(env))
    (
      config.get[String]("db.default.driver"),
      config.get[String]("db.default.url"),
      Map(
        "username" -> config.getOptional[String]("db.default.username").getOrElse(""),
        "password" -> config.getOptional[String]("db.default.password").getOrElse("")
      )
    )
  }

  override protected def beforeEach(): Unit = {
    super.beforeEach()
    val (driver, url, config) = dbConfig
    Databases.withDatabase(driver, url, config = config) { db =>
      initializeEvolutions(db)
    }
  }

  override protected def afterEach(): Unit = {
    val (driver, url, config) = dbConfig
    Databases.withDatabase(driver, url, config = config) { db =>
      cleanupEvolutions(db)
    }
    super.afterEach()
  }

  private def initializeEvolutions(database: Database):Unit = {
    Evolutions.cleanupEvolutions(database)
    Evolutions.applyEvolutions(database)
  }

  private def cleanupEvolutions(database: Database):Unit = {
    Evolutions.cleanupEvolutions(database)
  }
}
