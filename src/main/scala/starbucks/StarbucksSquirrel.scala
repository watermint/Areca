package starbucks

import areca.Rule
import squirrel.{Record => SquirrelRecord, Category}
import starbucks.{Record => StarbucksRecord}

import java.nio.file.Path

object StarbucksSquirrel extends Rule {
  def convert(inputPath: Path, outputPath: String): Unit = {
    SquirrelRecord.export(
      output = outputPath,
      records = StarbucksRecord.fromFile(inputPath).flatMap {
        starbucks =>
          fromStarbucks(starbucks)
      }
    )
  }

  def fromStarbucks(starbucks: StarbucksRecord): Option[SquirrelRecord] = {
    Some(
      SquirrelRecord(
        date = starbucks.date,
        description = starbucks.description,
        category = starbucks.description.contains("オートチャージ") match {
          case true => Category.adjustment
          case _ => Category.food
        },
        value = starbucks.receive - starbucks.payment,
        note = ""
      )
    )
  }
}
