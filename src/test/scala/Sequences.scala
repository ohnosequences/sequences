package ohnosequences.sequences.test

import org.scalatest.FunSuite

import ohnosequences.cosas._, types._
import ohnosequences.sequences._, alphabets._, sequences._

case object testImplementations {

  case object dnaAlphabet extends AnyAlphabet { val symbols = Set('A','T','C','G'); val label = toString }
  case object DNA extends SequenceType(dnaAlphabet) { lazy val label = toString; type Raw = Any }

  // String-based sequences
  class ListSequence[ST <: AnySequenceType { type Raw >: String }](seqType: ST) extends SequenceImpl(seqType) {

    type Raw = String

    lazy val label: String = s"${sequenceType.label} as String"

    // I can put this explicitly here, is fine
    def empty: ST := String =
      sequenceType := ""

    def concatenate(left: SequenceType := String, right: SequenceType := String): SequenceType := String =
      sequenceType := (left.value ++ right.value)
  }

  implicit val DNAList = new ListSequence(DNA)
}

class ListSequenceTests extends FunSuite {

  import testImplementations._

  test("can use syntax for sequences") {

    val zz    = DNA := "ATCCTCGTCT"
    val other = DNA := "ATCCCTTATCTCAGT"

    val buh = zz ++ other
  }
}
