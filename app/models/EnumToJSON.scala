package models

import play.api.libs.json.{Format, JsError, JsString, JsSuccess, Reads, Writes}

trait EnumToJSON { self: Enumeration =>

  /**
   * Implicit JSON Reads for this enumeration.
   * Tries to read an enum value by string representation.
   */
  implicit val enumReads: Reads[Value] = Reads {
    case JsString(str) =>
      try {
        JsSuccess(self.withName(str))
      } catch {
        case _: NoSuchElementException =>
          JsError(s"Unknown enum value: $str. Expected one of: ${self.values.mkString(", ")}")
      }
    case _ => JsError("String value expected")
  }

  /**
   * Implicit JSON Writes for this enumeration.
   * Converts an enum value to a JSON string.
   */
  implicit val enumWrites: Writes[Value] = Writes {
    enumValue => JsString(enumValue.toString)
  }

  /**
   * Implicit JSON Format (combines Reads and Writes) for this enumeration.
   */
  implicit val enumFormat: Format[Value] = Format(enumReads, enumWrites)
}
