package util

import java.time.{Instant, ZoneId}
import org.watermint.timesugar.TimeSugar
import scala.xml.{InputSource, Node}
import scala.xml.parsing.NoBindingFactoryAdapter
import nu.validator.htmlparser.sax.HtmlParser
import nu.validator.htmlparser.common.XmlViolationPolicy
import java.io.StringReader
import java.nio.file.Path
import scala.io.Source

case class Parser(timeZone: ZoneId = ZoneId.of("Asia/Tokyo")) {

  def number(text: String): Option[Int] = {
    text.replace(",", "").trim match {
      case n if n.matches("[+-]?\\d+") =>
        Some(Integer.parseInt(n))
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

  def html(path: Path, encoding: String): Option[Node] = {
    html(Source.fromFile(path.toFile, encoding).getLines().mkString)
  }

  def html(text: String): Option[Node] = {
    try {
      // reference:
      // http://www.mwsoft.jp/programming/scala/web_scraping.html
      val htmlParser = new HtmlParser
      val contentHandler = new NoBindingFactoryAdapter

      htmlParser.setNamePolicy(XmlViolationPolicy.ALLOW)
      htmlParser.setContentHandler(contentHandler)
      htmlParser.parse(new InputSource(new StringReader(text)))

      Some(contentHandler.rootElem)
    } catch {
      case e: Throwable =>
        e.printStackTrace()
        None
    }
  }
}
