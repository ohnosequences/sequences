package ohnosequences.sequences

import sequences._, alphabets._
import ohnosequences.cosas.typeSets._

case object finiteSequences {

  trait AnyFiniteSequence {

    type Alphabet <: AnyFiniteAlphabet
  }

  trait FiniteSequenceOver[A <: AnyFiniteAlphabet] extends AnyFiniteSequence {

    type Alphabet = A
  }

  def Empty[A0 <: AnyFiniteAlphabet]: EmptyFiniteSequence[A0] = new EmptyFiniteSequence[A0]
  class EmptyFiniteSequence[Alph <: AnyFiniteAlphabet]
  extends FiniteSequenceOver[Alph] {

    def ::[H0](h: H0)(implicit
      validSymbol: H0 ∈ Alph#Symbols
    )
    : ConsFiniteSequence[Alph,H0,EmptyFiniteSequence[Alph]] =
      new ConsFiniteSequence(h,this)
  }

  case class ConsFiniteSequence[
    Alph <: AnyFiniteAlphabet,
    H, T <: FiniteSequenceOver[Alph]
  ](val head: H, val tail: T)(implicit val validSymbol: H ∈ Alph#Symbols)
  extends FiniteSequenceOver[Alph] {

    def ::[H0](h: H0)(implicit
      validSymbol: H0 ∈ Alph#Symbols
    )
    : ConsFiniteSequence[Alph,H0,ConsFiniteSequence[Alph,H,T]] =
      new ConsFiniteSequence(h,this)
  }

  trait AnyFiniteSequenceType extends AnySequenceType {

    type Alphabet <: AnyFiniteAlphabet
  }
  abstract class FiniteSequenceType[A <: AnyFiniteAlphabet](val alphabet: A) extends AnySequenceType {

    type Alphabet = A
  }

  // TODO build sequences from an HList of symbols, all coming from the FiniteAlphabet (check predicate etc).
  // TODO this should be in ops for any sequence with finite alphabet

  /*
    Given a rep `A :: T :: C :: G :: G :: T` we can check statically whether that thing is from that alphabet. Then as we can create values of seqs from chars, we can use static mappings between alphabets to implement generic translations.

    Of course we need to be able to represent functions on alphabets. We can define `poly`s that

    1. have as input **symbols** from the domain alphabet
    2. and as output static sequences from the codomain alphabet

    Then given easy generic ops for building raw stuff from static sequences, we can implement a totally safe translation thing. We can also allow (statically defined) functions which would be defined on static *sequences* as input, returning something over a different alphabet. For example

    a :: t :: c -> atc

    Like in the canonical DNA -> aminoacid thing. Do we need coproducts here? if we would have them, we could have random access with a safe return type: `A or T or C or D` (over the typeset of symbols)


  */
}
