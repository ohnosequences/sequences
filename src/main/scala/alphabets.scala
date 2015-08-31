package ohnosequences.sequences

import ohnosequences.cosas._, types._

case object alphabets {

  /*
    The main restriction of this library is here: we are working with sequences over *finite* alphabets.
  */
  trait AnyAlphabet extends AnyType {

    // TODO can we do better?
    val symbols: Set[Char]
  }
}
