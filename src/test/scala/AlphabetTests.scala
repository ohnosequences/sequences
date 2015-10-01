package ohnosequences.sequences.tests

import ohnosequences.cosas._, typeSets._, fns._
import ohnosequences.sequences._, alphabets._

case object exampleFiniteAlphabets {

  case object nucleotides {

    case object A extends Symbol
    case object T extends Symbol
    case object C extends Symbol
    case object G extends Symbol
    // my beloved `N`
    case object N extends Symbol
    // RNA!
    case object U extends Symbol

    case object DNA   extends FiniteAlphabet(A :~: T :~: C :~: G :~: ∅)(MapToListOf.nonEmpty)
    case object RNA   extends FiniteAlphabet(U :~: ( DNA.symbols \ (T :~: ∅) ) )
    case object DNANs extends FiniteAlphabet(N :~: DNA.symbols)

    import finiteSequences._

    val z: FiniteSequenceOver[DNA.type]   = T::T::T::A::Empty[DNA.type]
    val zz: FiniteSequenceOver[RNA.type]  = U::U::A::A::A::Empty[RNA.type]
    // val zzz: FiniteSequenceOver[DNA.type] = U::U::U::A::T::Empty[DNA.type]
  }


  case object aminoacids {

    case object A extends Symbol
    case object C extends Symbol
    case object E extends Symbol
    case object G extends Symbol
    case object I extends Symbol
    case object L extends Symbol
    case object N extends Symbol
    case object Q extends Symbol
    case object S extends Symbol
    case object V extends Symbol
    case object X extends Symbol
    case object Z extends Symbol

    val allAminoacids = A :~: C :~: E :~: G :~: I :~: L :~: N :~: Q :~: S :~: V :~: X :~: Z :~: ∅

    case object Aminoacids extends FiniteAlphabet(allAminoacids)
  }



}
