package squirrel

import java.time.{ZoneId, Instant}
import org.watermint.timesugar.TimeSugar

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
