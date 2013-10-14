package shinsei

import java.time.{ZoneId, Instant}
import util.Parser
import java.nio.file.Path
import scala.io.Source

/**
 * 新生銀行 取引明細CSV.
 */
case class Record(date: Instant,
                  reference: String,
                  description: String,
                  withdrawal: Option[Int],
                  deposit: Option[Int],
                  balance: Int)

object Record {
  val parser = Parser(ZoneId.of("Asia/Tokyo"))

  def fromLine(line: String): Option[Record] = {
    val cols = line.split("\t")

    parser.date(cols(0)) match {
      case Some(d) =>
        Some(
          Record(
            date = d,
            reference = cols(1),
            description = cols(2),
            withdrawal = parser.number(cols(3)),
            deposit = parser.number(cols(4)),
            balance = parser.number(cols(5)).get
          )
        )
      case None =>
        // skip headers
        None
    }
  }

  def fromFile(path: Path): Seq[Record] = {
    Source.fromFile(path.toFile, "UTF-16").getLines().toList.flatMap {
      line =>
        fromLine(line)
    }
  }
}