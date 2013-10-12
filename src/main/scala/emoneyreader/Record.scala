package emoneyreader

import java.time.Instant
import squirrel.Account

case class Record(number: Long,
                  date: Instant,
                  category: Category,
                  note: String,
                  value: Long,
                  balance: Long,
                  account: Account) {

}

object Record {
}