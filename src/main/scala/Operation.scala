import java.nio.file.Path
import squirrel.Account

case class Operation(sourceType: Option[SourceType] = None,
                     sourcePath: Option[Path] = None,
                     destAccount: Option[Account] = None,
                     destType: Option[DestType] = None,
                     destPath: Option[String] = None) {

}
