package ohnosequences.sequences

import ohnosequences.cosas._, types._
import alphabets._

// TODO repeat the name shall not
case object sequences {

  /*
    The idea is that a sequence is any denotation of a sequence type, providing the API through a typeclass. We have an issue with value class restrictions: we cannot wrap (again) a value `seqType := S`. A possible solution could be

    - leave `S` free in the `AnySequence` typeclass, with a reference to sequence type
    - `AnySequence` can be then a normal value class
    - an implementation of `AnySequence` for a given sequence type should bound `S` and provide proper builders of denotations

    Generic code is written by requiring syntax instances for a (free) Sequencetype. Don't like.

    Quite differently, we could make `AnySequence` _a type_ which has implementations for all ops based on `S` (which would be `Raw`). Then we can write syntax for sequence based on that instance, only provided for something denoting that particular `AnySequence`. Code generic on the sequence implementation could be written by

    - requiring a particular sequence type
    - and an "implementation", an instance of `AnySequence` for that sequence type.
    - the `S` type is that of the implementation, and we shouldn't care about it.

    This is the best approach I think. It is pretty close to my pseudo-mythical "category theory in Scala" stuff.
  */

  /*
    This is the type that will be denoted using a particular implementation. I'm not totally sure about the point of bounding `Raw` here, maybe it is a good idea to leave it as `Any`
  */
  trait AnySequenceType extends AnyType {

    type Alphabet <: AnyAlphabet
    val alphabet: Alphabet
  }

  abstract class SequenceType[A <: AnyAlphabet](val alphabet: A) extends AnySequenceType {

    type Alphabet = A
  }

  // ops need the raw type to be uniform
  // they should be provided only for Sequence:= R values
  trait AnySequenceImpl extends AnyType {

    type SequenceType <: AnySequenceType
    val sequenceType: SequenceType

    // TODO actually useful? is that bound ever used?
    type Raw <: SequenceType#Raw

    def empty: SequenceType := Raw
    def concatenate(left: SequenceType:= Raw, right: SequenceType:= Raw): SequenceType:= Raw
  }

  abstract class SequenceImpl[ST <: AnySequenceType](val sequenceType: ST) extends AnySequenceImpl {

    type SequenceType = ST
  }

  // we can create a module abstraction which would fix the Ops value, if needed
  implicit def sequenceSyntax[
    S <: AnySequenceType,
    R <: S#Raw,
    Ops <: AnySequenceImpl { type SequenceType = S; type Raw = R }
  ]
  (seq: S := R)(implicit ops: Ops): SequenceSyntax[S,R,Ops] =
    SequenceSyntax(seq.value)

  case class SequenceSyntax[
    S <: AnySequenceType,
    R <: S#Raw,
    Ops <: AnySequenceImpl { type SequenceType = S; type Raw = R }
  ](val raw: R) extends AnyVal {

    def ++(right: S := R)(implicit ops: Ops): S := R =
      ops.concatenate( (ops.sequenceType: S) := raw, right )
  }
}
