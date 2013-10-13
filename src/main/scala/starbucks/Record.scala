package starbucks

import java.time.{ZoneId, Instant}
import util.Parser
import scala.xml.{Text, Node}
import java.nio.file.Path
import scala.io.Source
import scala.xml

/**
 * My STARBUCKS「残高照会・利用履歴」
 */
case class Record(date: Instant,
                  description: String,
                  receive: Int,
                  payment: Int)

object Record {
  val parser = Parser(ZoneId.of("Asia/Tokyo"))

  def fromRow(row: Node): Option[Record] = {
    val cols = row \ "td"
    Some(
      Record(
        date = parser.date(cols(0).text.trim).get,
        description = cols(1).text.trim,
        receive = parser.number(cols(2).text).get,
        payment = parser.number(cols(3).text).get
      )
    )
  }

  def fromFile(path: Path): Seq[Record] = {
    parser.html(path, "UTF-8") match {
      case Some(xml) =>
        // XPath: //*[@id="zandaka"]
        ((xml \\ "table" filter (_ \ "@id" contains Text("zandaka"))) \ "tbody" \ "tr").flatMap(tr => fromRow(tr))
      case _ => Seq()
    }
  }

}