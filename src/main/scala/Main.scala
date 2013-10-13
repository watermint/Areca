import java.nio.file.{Path, Files, FileSystems}
import rules.{Rule, SaisonSquirrel, EmoneySquirrel}

object Main {
  lazy val textSupportedSourceTypes = SourceType.sourceTypes.keys.mkString(", ")

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
        val rule: Option[Rule] = (op.sourceType, op.destType) match {
          case (Some(st: SourceTypeEmoney), Some(dt: DestTypeSquirrel)) => Some(EmoneySquirrel())
          case (Some(st: SourceTypeSaison), Some(dt: DestTypeSquirrel)) => Some(SaisonSquirrel())
          case _ => None
        }
        rule.foreach(_.convert(op.sourcePath.get, op.destPath.get))
    }
  }
}
