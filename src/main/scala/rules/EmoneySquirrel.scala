package rules

import squirrel.Tag
import squirrel.{Record => SquirrelRecord}
import squirrel.Category
import emoneyreader.{Record => EmoneyRecord, _}
import emoneyreader.CategoryTypePayment
import emoneyreader.CategoryTypeOther
import emoneyreader.CategoryTypeAdjustment
import emoneyreader.CategoryTypeBus

case class EmoneySquirrel(emoney: EmoneyRecord) {
  lazy val squirrelRecord: SquirrelRecord = {
    SquirrelRecord(
      date = emoney.date,
      description = emoney.category.name,
      category = EmoneySquirrel.category(emoney).get,
      value = emoney.value,
      note = emoney.note,
      tags = EmoneySquirrel.tags(emoney),
      account = emoney.account
    )
  }
}

object EmoneySquirrel {
  def category(emoney: EmoneyRecord): Option[Category] = {
    emoney.category.categoryType match {
      case c: CategoryTypeAdjustment => Some(SquirrelCategories.adjustment)
      case c: CategoryTypeBus => Some(SquirrelCategories.transport)
      case c: CategoryTypeOther => Some(SquirrelCategories.adjustment)
      case c: CategoryTypePayment => Some(SquirrelCategories.food)
      case c: CategoryTypeTrain => Some(SquirrelCategories.transport)
      case c: CategoryTypeVendingMachine => Some(SquirrelCategories.food)
      case _ => None
    }
  }

  def tags(emoney: EmoneyRecord): Seq[Tag] = {
    emoney.category.categoryType match {
      case c: CategoryTypeTrain => Seq(SquirrelTags.subway)
      case c: CategoryTypeBus => Seq(SquirrelTags.bus)
      case c: CategoryTypeVendingMachine => Seq(SquirrelTags.snack)
      case _ => Seq()
    }
  }

  object SquirrelCategories {
    val food = Category("01 食費")
    val transport = Category("02 交通費")
    val entertainment = Category("03 娯楽")
    val health = Category("04 健康")
    val livingCost = Category("05 生活費")
    val communication = Category("06 通信")
    val beauty = Category("07 美容・ファッション")
    val gift = Category("08 慶弔事")
    val equipment = Category("09 設備")
    val salary = Category("10 給与・賞与")
    val tax = Category("11 税金")
    val investment = Category("12 投資")
    val adjustment = Category("13 調整")
  }

  object SquirrelTags {
    val subway = Tag("subway")
    val bus = Tag("bus")
    val snack = Tag("snack")
  }

}