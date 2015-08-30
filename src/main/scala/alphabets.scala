package ohnosequences.sequences

import ohnosequences.cosas._, types._

case object alphabets {

  trait AnyAlphabet extends AnyType {

    // TODO can we do better?
    def symbols: Set[Char]
  }
}
