package shinsei

import areca.{Rule, Mapping}

object ShinseiMapping extends Mapping {
  val rules = Map(
    "squirrel" -> ShinseiSquirrel
  )

  val keys: Seq[String] = rules.keys.toSeq

  def rule(key: String): Option[Rule] = rules.get(key)

}
