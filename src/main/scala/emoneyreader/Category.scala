package emoneyreader

trait CategoryType

case class CategoryTypeTrain() extends CategoryType

case class CategoryTypeAdjustment() extends CategoryType

case class CategoryTypeBus() extends CategoryType

case class CategoryTypeVendingMachine() extends CategoryType

case class CategoryTypePayment() extends CategoryType

case class CategoryTypeOther() extends CategoryType

case class Category(name: String,
                    categoryType: CategoryType,
                    payment: Boolean) {

}

object Category {
  def apply(name: String): Category = {
    name match {
      case Categories.vendingMachine => Category(name = name, categoryType = CategoryTypeVendingMachine(), payment = true)
      case Categories.handyTerminal => Category(name = name, categoryType = CategoryTypePayment(), payment = true)
      case Categories.other => Category(name = name, categoryType = CategoryTypeOther(), payment = true)
      case Categories.trainOther
           | Categories.trainGate
           | Categories.trainAdjustment
           | Categories.trainDesk =>
        Category(name = name, categoryType = CategoryTypeTrain(), payment = true)
      case Categories.trainGateAutoCharge => Category(name = name, categoryType = CategoryTypeAdjustment(), payment = false)
      case Categories.bus => Category(name = name, categoryType = CategoryTypeBus(), payment = true)
      case _ => Category(name = name, categoryType = CategoryTypeOther(), payment = true)
    }
  }

  object Categories {
    val vendingMachine = "支払 (自販機)"
    val handyTerminal = "支払 (物販端末)"
    val other = "その他"
    val trainOther = "運賃支払 (その他)"
    val trainGate = "運賃支払 (改札機)"
    val trainAdjustment = "運賃支払 (乗継精算機)"
    val trainDesk = "運賃支払 (窓口処理機)"
    val trainGateAutoCharge = "オートチャージ (改札機)"
    val bus = "バス等運賃支払 (車載機)"
  }

}