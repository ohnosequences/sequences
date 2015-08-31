package ohnosequences.sequences.test

import org.scalatest.FunSuite

import ohnosequences.cosas._, types._
import ohnosequences.sequences._, alphabets._, sequences._

case object testImplementations {

  case object nucleotides extends AnyAlphabet {

    val symbols = Set('A','T','C','G');
    lazy val label = toString
  }
  case object DNA extends SequenceType(nucleotides)
  case object RNA extends SequenceType(nucleotides)

  // String-based sequences
  final class StringSequence[ST <: AnySequenceType](seqType: ST) extends Sequence(seqType) with Using[String] {

    lazy val label: String = toString

    @inline final val empty = ""
    @inline final def concatenate(a: String, b: String) = a ++ b

    @inline final def pickFrom(a: String) = a.filter(c => seqType.alphabet.symbols.contains(c))
  }

  // this can be abstracted as a module including an implementation etc
  implicit val stringyDNA = new StringSequence(DNA)
}

class StringSequenceTests extends FunSuite {

  import testImplementations._

  test("can use syntax for sequences") {

    val zz    = DNA as "ATCCTCGTCT"
    val other = DNA as "ATCCCTTATCTCAGT"

    val buh = zz ++ other
  }

  test("can filter funny stuff") {

    val notNucl = "NNBUUUUHHH"
    val ok = "ATCTCACATACCCCTTTAATCT"
    val funnyString = s"${notNucl}${ok}${notNucl}{ok}"

    assert { (DNA pickFrom funnyString) === (DNA as ok) }
    assert { (DNA pickFrom funnyString) === (DNA pickFrom ok) }

  }
}
