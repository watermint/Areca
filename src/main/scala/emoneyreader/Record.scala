package emoneyreader

import java.time.{ZoneId, Instant}
import java.nio.file.Path
import scala.io.{Codec, Source}
import util.Parser

/**
 * CSV Record format for Android EMoneyReader.
 *
 * @see https://play.google.com/store/apps/details?id=com.kaemashita.android.emoneyreader
 *
 * @param number no
 * @param date date
 * @param category type
 * @param note description
 * @param value value
 * @param balance balance
 */
case class Record(number: Long,
                  date: Instant,
                  category: Category,
                  note: String,
                  value: Long,
                  balance: Long) {

}

object Record {
  val parser = Parser(ZoneId.of("Asia/Tokyo"))

  def fromLine(line: String): Option[Record] = {
    val values = line.split(",")

    (
      parser.number(values(0)), // No
      parser.date(values(1)), // Date
      Category(values(2)), // Type
      values(3), // Description
      parser.number(values(4)), // Value
      parser.number(values(5)) // Balance
      ) match {
      case (Some(n: Int), Some(d), c: Category, note: String, Some(v), Some(b)) =>
        Some(
          Record(
            number = n,
            date = d,
            category = c,
            note = note,
            value = v,
            balance = b
          )
        )
      case _ => None
    }
  }

  def fromFile(path: Path): Seq[Record] = {
    Source.fromFile(path.toFile)(Codec.UTF8).getLines().toList.flatMap {
      line =>
        fromLine(line)
    }
  }
}