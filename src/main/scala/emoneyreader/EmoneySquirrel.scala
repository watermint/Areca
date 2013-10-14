package emoneyreader

import squirrel.{Record => SquirrelRecord, Category => SquirrelCategory}
import emoneyreader.{Record => EmoneyRecord}
import java.nio.file.Path
import areca.Rule

object EmoneySquirrel extends Rule {
  def convert(inputPath: Path, outputPath: String) = {
    SquirrelRecord.export(outputPath, fromFile(inputPath))
  }

  def fromEmoneyRecord(record: EmoneyRecord): SquirrelRecord = {
    SquirrelRecord(
      date = record.date,
      description = record.category.name,
      category = category(record).get,
      value = record.category.payment match {
        case true => -record.value
        case false => record.value
      },
      note = record.note
    )
  }

  def fromEmoneyRecords(records: Seq[EmoneyRecord]): Seq[SquirrelRecord] = {
    records.map(r => fromEmoneyRecord(r))
  }

  def fromFile(emoneyPath: Path): Seq[SquirrelRecord] = {
    fromEmoneyRecords(EmoneyRecord.fromFile(emoneyPath))
  }

  def category(emoney: EmoneyRecord): Option[SquirrelCategory] = {
    emoney.category.categoryType match {
      case c: CategoryTypeAdjustment => Some(SquirrelCategory.adjustment)
      case c: CategoryTypeBus => Some(SquirrelCategory.transport)
      case c: CategoryTypeOther => Some(SquirrelCategory.adjustment)
      case c: CategoryTypePayment => Some(SquirrelCategory.food)
      case c: CategoryTypeTrain => Some(SquirrelCategory.transport)
      case c: CategoryTypeVendingMachine => Some(SquirrelCategory.food)
      case _ => Some(SquirrelCategory.unsorted)
    }
  }

}