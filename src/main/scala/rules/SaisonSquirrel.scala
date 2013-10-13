package rules

import squirrel.{Record => SquirrelRecord, Category}
import saison.{Record => SaisonRecord}

import java.nio.file.Path

case class SaisonSquirrel() extends Rule {
  def convert(inputPath: Path, outputPath: String): Unit = {
    SquirrelRecord.export(
      outputPath,
      SaisonRecord.fromFile(inputPath).flatMap {
        saison =>
          fromSaison(saison)
      }
    )
  }

  def fromSaison(saison: SaisonRecord): Option[SquirrelRecord] = {
    Some(
      SquirrelRecord(
        date = saison.date,
        description = saison.payMonth + ": " + saison.description,
        category = Category.unsorted,
        value = -saison.amount,
        note = saison.note
      )
    )
  }
}