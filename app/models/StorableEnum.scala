package models

import anorm.{Column, ToStatement, TypeDoesNotMatch}

trait StorableEnum {
  self: Enumeration =>

  implicit def rowToEnum: Column[Value] = {
    Column.nonNull[Value] { (value, _) =>
      try {
        Right(withName(value.toString))
      } catch {
        case _: Throwable => Left(TypeDoesNotMatch(
          s"Cannot convert $value: ${value.asInstanceOf[AnyRef].getClass} to ${getClass.getName}"))
      }
    }
  }

  implicit def enumToStatement: ToStatement[Value] =
    (s: java.sql.PreparedStatement, index: Int, value: Value) => s.setObject(index, value.toString)
}

