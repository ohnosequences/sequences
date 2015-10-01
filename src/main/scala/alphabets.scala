package ohnosequences.sequences

import ohnosequences.cosas._, types._, typeSets._, fns._


case object alphabets {

  /*
    The main restriction of this library is here: we are working with sequences over *finite* alphabets.
  */
  trait AnyAlphabet extends AnyType {

    // TODO can we do better?
    val symbolsChar: Set[Char]
  }

  // char could be anything here
  trait AnySymbol { val char: Char }
  trait Symbol extends AnySymbol { lazy val char = toString.head }

  case object symbolChar extends DepFn1[AnySymbol, Char] {

  implicit def default[S <: AnySymbol]: App1[symbolChar.type, S, Char] = symbolChar at { s: AnySymbol => s.char }
  }

  trait AnyFiniteAlphabet extends AnyAlphabet {

    type Symbols <: AnyTypeSet.Of[AnySymbol]
    val symbols: Symbols

    implicit val mapSymbolChars: App2[mapToListOf[Char], symbolChar.type, Symbols, List[Char]]

    lazy val symbolsChar: Set[Char] = (symbols mapToList symbolChar).toSet

    lazy val label: String = toString
  }

  abstract class FiniteAlphabet[Ss <: AnyTypeSet.Of[AnySymbol]](val symbols: Ss)(
    implicit val mapSymbolChars: App2[mapToListOf[Char], symbolChar.type, Ss, List[Char]]
  )
  extends AnyFiniteAlphabet {

    type Symbols = Ss
  }

}
