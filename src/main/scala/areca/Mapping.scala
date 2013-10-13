package areca

trait Mapping {
  val keys: Seq[String]

  def rule(key: String): Option[Rule]
}
