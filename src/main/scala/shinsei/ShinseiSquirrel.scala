package shinsei

import areca.Rule
import java.nio.file.Path
import squirrel.{Record => SquirrelRecord, Category => SquirrelCategory}
import shinsei.{Record => ShinseiRecord}

/**
  */
object ShinseiSquirrel extends Rule {
  def convert(inputPath: Path, outputPath: String): Unit = {
    SquirrelRecord.export(
      output = outputPath,
      records = ShinseiRecord.fromFile(inputPath).map {
        shinsei =>
          SquirrelRecord(
            date = shinsei.date,
            description = shinsei.description,
            category = category(shinsei),
            value = (shinsei.deposit, shinsei.withdrawal) match {
              case (Some(d), Some(w)) => d - w
              case (Some(d), None) => d
              case (None, Some(w)) => -w
              case _ => 0
            },
            note = "残高: " + shinsei.balance
          )
      }
    )
  }

  def category(shinsei: ShinseiRecord): SquirrelCategory = {
    shinsei.description match {
      // Withdraw
      case "ATM 現金出金（提携取引）" | "ATM 現金出金" => SquirrelCategory.adjustment

      // Tax
      case "地方税" | "国税" => SquirrelCategory.tax

      // Interest
      case "税引前利息" => SquirrelCategory.investment

      // Utilities
      case "ﾄｳｷﾖｳﾄｽｲﾄﾞｳｷﾖｸ" => SquirrelCategory.livingCost

      // Credit cards
      case "ｾｿﾞﾝ" | "ﾋﾞﾕ-ｶ-ﾄﾞ" =>
        SquirrelCategory.adjustment

      case _ => SquirrelCategory.unsorted
    }
  }
}
