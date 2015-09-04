package ohnosequences.sequences

import ohnosequences.cosas._, types._, typeSets._, ops.typeSets.MapToList


case object alphabets {

  /*
    The main restriction of this library is here: we are working with sequences over *finite* alphabets.
  */
  trait AnyAlphabet extends AnyType {

    // TODO can we do better?
    val symbolsChar: Set[Char]
  }

  trait AnySymbol { val char: Char }
  trait Symbol extends AnySymbol { lazy val char = toString.head }

  case object symbolChar extends shapeless.Poly1 { implicit def symbolChar[S <: AnySymbol] = at[S]{ s => s.char } }

  trait AnyFiniteAlphabet extends AnyAlphabet {

    type Symbols <: AnyTypeSet.Of[AnySymbol]
    val symbols: Symbols

    implicit val mapSymbolChars: (symbolChar.type MapToList Symbols) { type O = Char }

    lazy val symbolsChar: Set[Char] = (symbols mapToList symbolChar).toSet

    lazy val label = toString
  }

  abstract class FiniteAlphabet[Ss <: AnyTypeSet.Of[AnySymbol]](val symbols: Ss)(
    implicit val mapSymbolChars: (symbolChar.type MapToList Ss) { type O = Char }
  )
  extends AnyFiniteAlphabet {

    type Symbols = Ss
  }

}
