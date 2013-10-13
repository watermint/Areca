package rules

import java.nio.file.Path

trait Rule {
  def convert(inputPath: Path, outputPath: String)
}
