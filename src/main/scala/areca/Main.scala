package areca

object Main {
  def main(args: Array[String]): Unit = {
    Operation.argParser.parse(args, Operation()) map {
      op =>
        val rule: Option[Rule] = (op.sourceType, op.destType) match {
          case (Some(sourceType), Some(destType)) =>
            Mappings.mappings.get(sourceType) match {
              case Some(m) => m.rule(destType)
              case _ => None
            }
          case _ => None
        }
        rule match {
          case Some(r) => r.convert(op.sourcePath.get, op.destPath.get)
          case _ => println("Unsupported source/target mapping")
        }
    }
  }
}
