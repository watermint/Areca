package areca


trait DestType

case class DestTypeSquirrel() extends DestType

object DestType {
  lazy val destTypes = Map(
    "squirrel" -> DestTypeSquirrel()
  )
}