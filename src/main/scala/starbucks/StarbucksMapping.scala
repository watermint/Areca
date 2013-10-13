package starbucks

import areca.{Rule, Mapping}

object StarbucksMapping extends Mapping {
  val rules = Map(
    "squirrel" -> StarbucksSquirrel
  )

  val keys: Seq[String] = rules.keys.toSeq

  def rule(key: String): Option[Rule] = rules.get(key)
}
