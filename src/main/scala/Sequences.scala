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
  trait AnySequenceType {

    type Alphabet <: AnyAlphabet
    val alphabet: Alphabet
  }
  abstract class SequenceType[A <: AnyAlphabet](val alphabet: A) extends AnySequenceType {

    type Alphabet = A
  }

  implicit def sequenceTypeOps[
    ST <: AnySequenceType,
    S <: AnySequence { type SequenceType = ST }
  ]
  (seqType: ST)(implicit seq: S): SequenceTypeOps[ST,S] =
    SequenceTypeOps(seqType)

  case class SequenceTypeOps[
    ST <: AnySequenceType,
    S <: AnySequence { type SequenceType = ST }
  ]
  (val seqType: ST) extends AnyVal {

    // unsafe, does not check anything
    final def as(raw: S#Raw)(implicit seq: S): ValueOf[S] = (seq:S) := raw

    final def pickFrom(raw: S#Raw)(implicit seq: AnySequence.is[S]): ValueOf[S] = (seq:S) := seq.pickFrom(raw: S#Raw)

    // TODO apply's based on particular implementations, that can actually check stuff
  }

  // This is an implementation of a sequence
  trait AnySequence extends AnyType {

    type SequenceType <: AnySequenceType
    val sequenceType: SequenceType

    // TODO actually useful? is that bound ever used?
    def empty: Raw
    def concatenate(l: Raw, r: Raw): Raw

    // those valid symbols from ...
    def pickFrom(l: Raw): Raw
  }

  trait AnyConvert {

    type From <: AnySequence
    type To <: AnySequence

    def apply(from: From#Raw): To#Raw
  }

  case object AnySequence {

    type is[S <: AnySequence] = S with AnySequence { type Raw = S#Raw; type SequenceType = S#SequenceType }
  }

  abstract class Sequence[ST <: AnySequenceType](val sequenceType: ST) extends AnySequence { type SequenceType = ST }
  trait Using[X] extends AnySequence { type Raw = X }

  // we can create a module abstraction which would fix the Ops value, if needed
  implicit def sequenceSyntax[S <: AnySequence](seq: ValueOf[S])(implicit s: S): SequenceSyntax[S] =
    SequenceSyntax(seq.value: S#Raw)

  case class SequenceSyntax[S <: AnySequence](val raw: S#Raw) extends AnyVal {

    final def ++(right: ValueOf[S])(implicit s: AnySequence.is[S]): ValueOf[S] =
      (s: S) := s.concatenate((raw: S#Raw), (right.value: S#Raw))
  }
}
