package squirrel

import java.time.{ZoneId, Instant}
import org.watermint.timesugar.TimeSugar
import scalax.io.{Codec, Resource}
import java.io.File

/**
 * Squirrelへのインポート用.
 *
 * @param date 日付
 * @param description 明細
 * @param category カテゴリ
 * @param value 金額
 * @param note メモ
 */
case class Record(date: Instant,
                  description: String,
                  category: Category,
                  value: Long,
                  note: String) {
  lazy val timeZone = ZoneId.of("Asia/Tokyo")

  lazy val formattedDate = TimeSugar.format(date, "yyyy/MM/dd", timeZone)

  lazy val line = formattedDate +
    "," + description +
    "," + value +
    "," + note +
    "," + category.name
}

object Record {
  def export(output: String, records: Seq[Record]) = {
    Resource.fromFile(new File(output)).write(
      records.map(_.line).mkString("\n")
    )(Codec.UTF8)
  }
}