package squirrel

case class Account(name: String) {

}

object Account {
  val accounts = Map(
    "lumine" -> Account("SUICA-Lumine"),
    "mobile" -> Account("SUICA-Mobile")
  )
}
