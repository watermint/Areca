package areca

import rules.{StarbucksSquirrel, SaisonSquirrel, EmoneySquirrel}

object Main {
  def main(args: Array[String]): Unit = {
    Operation.argParser.parse(args, Operation()) map {
      op =>
        val rule: Option[Rule] = (op.sourceType, op.destType) match {
          case (Some(st: SourceTypeEmoney), Some(dt: DestTypeSquirrel)) => Some(EmoneySquirrel())
          case (Some(st: SourceTypeSaison), Some(dt: DestTypeSquirrel)) => Some(SaisonSquirrel())
          case (Some(st: SourceTypeStarbucks), Some(dt: DestTypeSquirrel)) => Some(StarbucksSquirrel())
          case _ => None
        }
        rule.foreach(_.convert(op.sourcePath.get, op.destPath.get))
    }
  }
}
