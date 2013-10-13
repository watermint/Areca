package squirrel

case class Category(name: String)

/**
 * Squirrelに定義しておいたカテゴリ.
 * ここは設定的にかえられるようにしたいが、Rule側でもマッピングをするため
 * ある程度固定的に設定しておくことにする.
 */
object Category {
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
  val unsorted = Category("14 未分類")
}
