package services

import anorm.{Macro, RowParser, SqlStringInterpolation}
import models.{Pid, PidType}
import play.api.Configuration
import play.api.db.Database

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

case class SqlPidService @Inject()(db: Database, config: Configuration)(implicit ec: ExecutionContext) extends PidService {

  private implicit val pidParser: RowParser[Pid] = Macro.parser[Pid]("ptype", "value", "target")

  override def findAll(ptype: PidType.Value): Future[Seq[Pid]] = Future {
    db.withConnection{ implicit conn =>
      SQL"SELECT ptype, value, target FROM pids WHERE ptype = $ptype::pid_type"
        .as(pidParser.*)
    }
  }(ec)

  override def findById(ptype: PidType.Value, value: String): Future[Option[Pid]] = Future {
    db.withConnection { implicit conn =>
      SQL"SELECT ptype, value, target FROM pids WHERE ptype = $ptype::pid_type AND value = $value"
        .as(pidParser.singleOpt)
    }
  }(ec)

  override def create(ptype: PidType.Value, value: String, target: String, client: String): Future[Pid] = Future {
    db.withConnection { implicit conn =>
      SQL"""INSERT INTO pids (ptype, value, target, client) VALUES ($ptype::pid_type, $value, $target, $client)
            RETURNING ptype, value, target"""
        .as(pidParser.single)
    }
  }(ec)

  override def update(ptype: PidType.Value, value: String, target: String): Future[Pid] = Future {
    db.withConnection { implicit conn =>
      SQL"""UPDATE pids SET target = $target
            WHERE ptype = $ptype::pid_type AND value = $value
            RETURNING ptype, value, target"""
        .as(pidParser.single)
    }
  }(ec)

  override def delete(ptype: PidType.Value, value: String): Future[Boolean] = Future {
    db.withConnection { implicit conn =>
      SQL"DELETE FROM pids WHERE ptype = $ptype::pid_type AND value = $value"
        .executeUpdate() == 1
    }
  }(ec)
}
