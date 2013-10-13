package saison

import areca.{Rule, Mapping}

object SaisonMapping extends Mapping {
  val rules = Map(
    "squirrel" -> SaisonSquirrel
  )

  val keys: Seq[String] = rules.keys.toSeq

  def rule(key: String): Option[Rule] = rules.get(key)

}
