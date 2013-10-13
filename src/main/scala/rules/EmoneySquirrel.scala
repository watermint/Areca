package rules

import squirrel.{Record => SquirrelRecord, Category}
import emoneyreader.{Record => EmoneyRecord, _}
import emoneyreader.CategoryTypePayment
import emoneyreader.CategoryTypeOther
import emoneyreader.CategoryTypeAdjustment
import emoneyreader.CategoryTypeBus
import java.nio.file.Path

case class EmoneySquirrel() extends Rule {
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

  def category(emoney: EmoneyRecord): Option[Category] = {
    emoney.category.categoryType match {
      case c: CategoryTypeAdjustment => Some(Category.adjustment)
      case c: CategoryTypeBus => Some(Category.transport)
      case c: CategoryTypeOther => Some(Category.adjustment)
      case c: CategoryTypePayment => Some(Category.food)
      case c: CategoryTypeTrain => Some(Category.transport)
      case c: CategoryTypeVendingMachine => Some(Category.food)
      case _ => None
    }
  }

}