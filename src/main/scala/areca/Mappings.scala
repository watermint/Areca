package areca

import emoneyreader.EmoneyMapping
import saison.SaisonMapping
import starbucks.StarbucksMapping

object Mappings {
  val mappings: Map[String, Mapping] = Map(
    "emoney" -> EmoneyMapping,
    "saison" -> SaisonMapping,
    "starbucks" -> StarbucksMapping
  )
}
