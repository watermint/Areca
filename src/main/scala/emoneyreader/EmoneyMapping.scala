package emoneyreader

import areca.{Rule, Mapping}

object EmoneyMapping extends Mapping {
  val rules = Map(
    "squirrel" -> EmoneySquirrel
  )

  val keys: Seq[String] = rules.keys.toSeq

  def rule(key: String): Option[Rule] = rules.get(key)
}
