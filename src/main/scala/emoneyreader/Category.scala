package emoneyreader

trait CategoryType
case class CategoryTypeTrain() extends CategoryType
case class CategoryTypeAdjustment() extends CategoryType
case class CategoryTypeBus() extends CategoryType
case class CategoryTypeVendingMachine() extends CategoryType
case class CategoryTypePayment() extends CategoryType
case class CategoryTypeOther() extends CategoryType

case class Category(name: String,
                    categoryType: CategoryType) {

}

object Category {
  def apply(name: String): Category = {
    name match {
      case Categories.vendingMachine => Category(name, CategoryTypeVendingMachine())
      case Categories.handyTerminal => Category(name, CategoryTypePayment())
      case Categories.other => Category(name, CategoryTypeOther())
      case Categories.trainOther
           | Categories.trainGate
           | Categories.trainAdjustment
           | Categories.trainDesk =>
        Category(name, CategoryTypeTrain())
      case Categories.trainGateAutoCharge => Category(name, CategoryTypeAdjustment())
      case Categories.bus => Category(name, CategoryTypeBus())
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