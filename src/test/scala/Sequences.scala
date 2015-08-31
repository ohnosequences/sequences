package ohnosequences.sequences.test

import org.scalatest.FunSuite

import ohnosequences.cosas._, types._
import ohnosequences.sequences._, alphabets._, sequences._

case object testImplementations {

  case object dnaAlphabet extends AnyAlphabet { val symbols = Set('A','T','C','G'); lazy val label = toString }
  case object DNA extends SequenceType(dnaAlphabet)

  // String-based sequences
  final class StringSequence[ST <: AnySequenceType](seqType: ST) extends Sequence(seqType) with Using[String] {

    lazy val label: String = toString

    def empty = ""
    def concatenate(a: String, b: String) = a ++ b
  }

  implicit val stringyDNA = new StringSequence(DNA)
}

class StringSequenceTests extends FunSuite {

  import testImplementations._

  test("can use syntax for sequences") {

    val zz    = DNA as "ATCCTCGTCT"
    val other = DNA as "ATCCCTTATCTCAGT"

    val buh = zz ++ other
  }
}
