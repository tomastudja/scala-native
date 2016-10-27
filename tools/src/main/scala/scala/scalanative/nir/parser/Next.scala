package scala.scalanative
package nir
package parser

import fastparse.all._

object Next extends Base[nir.Next] {

  import Base.IgnoreWhitespace._

  val Label =
    P(Local.parser ~ ("(" ~ Val.parser.rep(sep = ",") ~ ")").? map {
      case (name, args) => nir.Next.Label(name, args getOrElse Seq())
    })
  val Succ = P("succ" ~ Local.parser map (nir.Next.Succ(_)))
  val Fail = P("fail" ~ Local.parser map (nir.Next.Fail(_)))
  val Case =
    P("case" ~ Val.parser ~ "=>" ~ Local.parser map {
      case (value, name) => nir.Next.Case(value, name)
    })
  override val parser: P[nir.Next] = Label | Succ | Fail | Case
}
