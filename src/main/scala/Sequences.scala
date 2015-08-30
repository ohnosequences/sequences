package ohnosequences.sequences

import ohnosequences.cosas._, types._
import alphabets._

// TODO repeat the name shall not
case object sequences {

  trait AnySequenceType extends AnyType {

    type Alphabet <: AnyAlphabet
    val alphabet: Alphabet
  }

  /*
    The idea is that a sequence is any denotation of a sequence type, providing the API through a typeclass. We have an issue with value class restrictions: we cannot wrap (again) a value `seqType := S`. A possible solution could be

    - leave `S` free in the `AnySequence` typeclass, with a reference to sequence type
    - `AnySequence` can be then a normal value class
    - an implementation of `AnySequence` for a given sequence type should bound `S` and provide proper builders of denotations

    Generic code is written by requiring syntax instances for a (free) S type. Don't like.

    Quite differently, we could make `AnySequence` _a type_ which has implementations for all ops based on `S`. Then we can write syntax for sequence based on that instance, only provided for something denoting that particular `AnySequence`. Code generic on the sequence implementation could be written by

    - requiring a particular sequence type
    - and an "implementation", an instance of `AnySequence` for that sequence type.
    - the `S` type is that of the implementation, and we shouldn't care about it.

    This is the best approach I think. It is pretty close to my pseudo-mythical "category theory in Scala" stuff.
  */
}
