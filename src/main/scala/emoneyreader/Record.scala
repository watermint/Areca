package emoneyreader

import java.time.{ZoneId, Instant}
import java.nio.file.Path
import scala.io.{Codec, Source}
import org.watermint.timesugar.TimeSugar

case class Record(number: Long,
                  date: Instant,
                  category: Category,
                  note: String,
                  value: Long,
                  balance: Long) {

}

object Record {

  lazy val timeZone = ZoneId.of("Asia/Tokyo")

  def number(text: String): Option[Int] = {
    text match {
      case n if n.matches("[+-]?\\d+") =>
        Some(Integer.parseInt(text))
      case _ => None
    }
  }

  def date(text: String): Option[Instant] = {
    val d = TimeSugar.parseWithPatterns(text, timeZone, "yyyy/MM/dd", "yyyy/MM/dd HH:mm")
    d.isPresent match {
      case true => Some(d.get())
      case _ => None
    }
  }

  def fromLine(line: String): Option[Record] = {
    val values = line.split(",")

    (number(values(0)), date(values(1)), Category(values(2)), values(3), number(values(4)), number(values(5))) match {
      case (Some(n: Int), Some(d), c: Category, note: String, Some(v), Some(b)) =>
        Some(
          Record(
            number = n.toLong,
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