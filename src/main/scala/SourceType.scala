
trait SourceType

case class SourceTypeEmoney() extends SourceType

object SourceType {
  lazy val sourceTypes = Map(
    "emoney" -> SourceTypeEmoney()
  )
}