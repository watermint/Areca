import java.nio.file.Path

case class Operation(sourceType: Option[SourceType] = None,
                     sourcePath: Option[Path] = None,
                     destType: Option[DestType] = None,
                     destPath: Option[String] = None) {
}
