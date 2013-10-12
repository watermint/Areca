import squirrel.Account

trait SourceType

case class SourceTypeEmoney(account: Account) extends SourceType