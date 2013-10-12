import java.nio.file.FileSystems
import squirrel.Account

object Main {
  val argParser = new scopt.OptionParser[Operation]("areca") {
    head("areca", "1.0")
    opt[(String, String)]('s', "source") action {
      case ((typeName, option), o) =>
        typeName match {
          case "emoney" =>
            o.copy(sourceType = Some(SourceTypeEmoney(
              Account.accounts.get(option).get)
            ))
        }
    } keyValueName("<sourceType>", "<account name>") text {
      "source file type and account name: " + Account.accounts.keys.mkString(", ")
    }
    opt[String]('i', "input") action {
      case (input, o) =>
        o.copy(
          sourcePath = Some(FileSystems.getDefault.getPath(input))
        )
    } text {
      "input file name"
    }
    opt[String]('d', "dest") action {
      case (dest, o) =>
        dest match {
          case "squirrel" =>
            o.copy(destType = Some(DestTypeSquirrel()))
        }
    } text {
      "destination type"
    }
    opt[String]('o', "output") action {
      case (output, o) =>
        o.copy(destPath = Some(output))
    } text {
      "output file name"
    }
  }

  def main(args: Array[String]): Unit = {
    argParser.parse(args, Operation()) map {
      op =>
        println(op)
    } getOrElse {
      argParser.usage
    }
  }
}
