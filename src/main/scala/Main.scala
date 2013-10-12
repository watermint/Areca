import java.nio.file.{Files, FileSystems}
import squirrel.Account

object Main {
  lazy val textSupportedSourceTypes = SourceType.sourceTypes.keys.mkString(", ")

  lazy val textSupportedAccountNames = Account.accounts.keys.mkString(", ")

  lazy val textSupportedDestTypes = DestType.destTypes.keys.mkString(", ")

  val argParser = new scopt.OptionParser[Operation]("areca") {
    head("areca", "1.0")

    //
    // Source Type
    //
    opt[String]('s', "source") action {
      case (typeName, o) =>
        SourceType.sourceTypes.get(typeName) match {
          case Some(t) => o.copy(sourceType = Some(t))
          case None => o
        }
    } validate {
      typeName =>
        SourceType.sourceTypes.get(typeName) match {
          case Some(t) => success
          case _ => failure("unknown type. supported types are follows: " + textSupportedSourceTypes)
        }
    } text {
      "source file type: " + textSupportedSourceTypes
    } required()

    //
    // Source File Path
    //
    opt[String]('i', "input") action {
      case (input, o) =>
        o.copy(
          sourcePath = Some(FileSystems.getDefault.getPath(input))
        )
    } validate {
      input =>
        val path = FileSystems.getDefault.getPath(input)
        if (Files.exists(path)) {
          success
        } else {
          failure("file not found: " + path.toString)
        }
    } text {
      "input file name"
    } required()

    //
    // Dest Account
    //
    opt[String]('a', "account") action {
      case (account, o) =>
        Account.accounts.get(account) match {
          case a: Some[Account] => o.copy(destAccount = a)
          case _ => o
        }
    } validate {
      account =>
        Account.accounts.get(account) match {
          case Some(a) => success
          case _ => failure("unknown account: " + account + ", supported accounts are: " + textSupportedAccountNames)
        }
    } text {
      "target account: " + textSupportedAccountNames
    } required()

    //
    // Dest Type
    //
    opt[String]('d', "dest") action {
      case (dest, o) =>
        DestType.destTypes.get(dest) match {
          case Some(d) => o.copy(destType = Some(d))
          case _ => o
        }
    } validate {
      dest =>
        DestType.destTypes.get(dest) match {
          case Some(d) => success
          case _ => failure("unknown dest type: " + dest + ", supported types are: " + textSupportedDestTypes)
        }
    } text {
      "destination type: " + textSupportedDestTypes
    } required()

    //
    // Output file
    //
    opt[String]('o', "output") action {
      case (output, o) =>
        o.copy(destPath = Some(output))
    } text {
      "output file name"
    } required()
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
