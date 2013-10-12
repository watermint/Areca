package squirrel

case class Account(name: String) {

}

object Account {
  val defaultAccount = Account("SUICA-Mobile")
  val accounts = Map(
    "lumine" -> Account("SUICA-Lumine"),
    "mobile" -> Account("SUICA-Mobile")
  )
}
