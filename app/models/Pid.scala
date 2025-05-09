package models

object PidType extends Enumeration with StorableEnum {
  val DOI = Value("DOI")
  val ARK = Value("ARK")
  val HANDLE = Value("HANDLE")
  val PURL = Value("PURL")
  val URN = Value("URN")
}

case class Pid(
  ptype: PidType.Value,
  value: String,
  target: String,
  tombstone: Option[Tombstone] = None,
)
