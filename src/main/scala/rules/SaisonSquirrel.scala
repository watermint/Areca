package rules

import squirrel.{Record => SquirrelRecord, Category}
import saison.{Record => SaisonRecord}

import java.nio.file.Path
import areca.Rule

case class SaisonSquirrel() extends Rule {
  def convert(inputPath: Path, outputPath: String): Unit = {
    SquirrelRecord.export(
      output = outputPath,
      records = SaisonRecord.fromFile(inputPath).flatMap {
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
