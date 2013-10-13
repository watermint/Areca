package areca


trait SourceType

case class SourceTypeEmoney() extends SourceType

case class SourceTypeSaison() extends SourceType

case class SourceTypeStarbucks() extends SourceType

object SourceType {
  lazy val sourceTypes = Map(
    "emoney" -> SourceTypeEmoney(),
    "saison" -> SourceTypeSaison(),
    "starbucks" -> SourceTypeStarbucks()
  )
}