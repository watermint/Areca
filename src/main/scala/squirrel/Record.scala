package squirrel

import java.time.Instant

case class Record(date: Instant,
                  description: String,
                  category: Category,
                  value: Long,
                  note: String,
                  tags: Seq[Tag],
                  account: Account) {

}
