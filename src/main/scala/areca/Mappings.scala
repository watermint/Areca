package areca

import emoneyreader.EmoneyMapping
import saison.SaisonMapping
import starbucks.StarbucksMapping
import shinsei.ShinseiMapping

object Mappings {
  val mappings: Map[String, Mapping] = Map(
    "emoney" -> EmoneyMapping,
    "saison" -> SaisonMapping,
    "shinsei" -> ShinseiMapping,
    "starbucks" -> StarbucksMapping
  )
}
