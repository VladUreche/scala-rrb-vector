package scala.collection.immutable.vectortests

import org.scalatest._


//import scala.collection.immutable.rrbprototype.Vector

//import scala.collection.immutable.vector.Vector

//import scala.collection.immutable.rbvector.{RBVector => Vector}

import scala.collection.immutable.rrbvector.{RRBVector => Vector}


abstract class BaseVectorSpec[A] extends WordSpec {

    def element(n: Int): A

    "The Vector companion object" should {

    }
    "A Vector" when {
        "empty" should {
            def vector = Vector.empty[A]
            "have size 0" in assertResult(0)(vector.size)
            "have length 0" in assertResult(0)(vector.length)
            "return true when isEmpty is invoked" in assertResult(true)(vector.isEmpty)
            "return false when nonEmpty is invoked" in assertResult(false)(vector.nonEmpty)
            "return false with any index when isDefinedAt is invoked" in {
                assertResult(false)(vector.isDefinedAt(-1))
                assertResult(false)(vector.isDefinedAt(0))
                assertResult(false)(vector.isDefinedAt(1))
            }
            "produce UnsupportedOperationException when head is invoked" in {
                intercept[UnsupportedOperationException](vector.head)
            }
            "produce UnsupportedOperationException when last is invoked" in {
                intercept[UnsupportedOperationException](vector.last)
            }
            "produce UnsupportedOperationException when init is invoked" in {
                intercept[UnsupportedOperationException](vector.init)
            }
            "produce UnsupportedOperationException when tail is invoked" in {
                intercept[UnsupportedOperationException](vector.tail)
            }
            "return -n for lengthCompare(n)" in {
                for (i <- -10 to 10)
                    assertResult(-i)(vector.lengthCompare(i))
            }
            "return an empty vector when drop is invoked" in {
                assertResult(vector)(vector.drop(-1))
                assertResult(vector)(vector.drop(0))
                assertResult(vector)(vector.drop(1))
            }
            "return an empty vector when dropRight is invoked" in {
                assertResult(vector)(vector.dropRight(-1))
                assertResult(vector)(vector.dropRight(0))
                assertResult(vector)(vector.dropRight(1))
            }
            "return an empty vector when take is invoked" in {
                assertResult(vector)(vector.take(-1))
                assertResult(vector)(vector.take(0))
                assertResult(vector)(vector.take(1))
            }
            "return an empty vector when takeRight is invoked" in {
                assertResult(vector)(vector.takeRight(-1))
                assertResult(vector)(vector.takeRight(0))
                assertResult(vector)(vector.takeRight(1))
            }
            "return an empty iterator" in {
                assert(vector.iterator.isEmpty)
            }
            "return an empty vector when slice is invoked" in {
                assertResult(vector)(vector.slice(0, 1))
                assertResult(vector)(vector.slice(0, -1))
                assertResult(vector)(vector.slice(0, 0))
                assertResult(vector)(vector.slice(0, 2))
            }
            "return a vector with exactly one element when :+ is invoked" in {
                def v = vector :+ element(42)
                assertResult(1)(v.length)
                assertResult(Vector(element(42)))(v)
            }
            "return a vector with exactly one element when +: is invoked" in {
                def v = element(42) +: vector
                assertResult(1)(v.length)
                assertResult(Vector(element(42)))(v)
            }
        }

        for (n <- Seq(1, 8, 16, 32, 33, 64, 65, 1024, 1025)) {
            s"contains $n elements" should {
                def vector = Vector.tabulate(n)(i => element(i))
                testNonEmptyVectorProperties(vector, n)
                s"return the i-th element of Vector.tabulate($n)(i => element(i)) should be i when apply is invoked" in {
                    for (i <- 0 until n) assertResult(element(i))(vector(i))
                }
            }


        }
        "two vectors are concatenated" when {
            for (n <- Seq(1, 5, 8, 16, 17, 32, 33, 53, 64, 65, 1024, 1025)) {
                def left = Vector.tabulate(n)(i => element(i))
                s"left vector contains $n elements" when {
                    for (m <- Seq(1, 5, 8, 16, 17, 32, 33, 53, 64, 65, 1024, 1025)) {
                        def right = Vector.tabulate(m)(i => element(i))
                        s"right vector contains $m elements" should {
                            def vector = left ++ right
                            testNonEmptyVectorProperties(vector, n + m)
                            "is equal (content/order wise) to appending all elements one by one" in {
                                var b = Vector.empty[A]
                                for (e <- left) b = b :+ e
                                for (e <- right) b = b :+ e
                                assert(b === vector)
                            }
                        }
                    }
                }
            }
        }
    }

    def testNonEmptyVectorProperties(vector: => Vector[A], n: Int) = {
        s"have size $n" in assertResult(n)(vector.size)
        s"have length $n" in assertResult(n)(vector.length)
        "return false when isEmpty is invoked" in assertResult(false)(vector.isEmpty)
        "return true when nonEmpty is invoked" in assertResult(true)(vector.nonEmpty)
        s"return true with any index from 0 until $n when isDefinedAt is invoked" in {
            for (i <- 0 until n) assertResult(true)(vector.isDefinedAt(i))
        }
        "return false with any negative index when isDefinedAt is invoked" in {
            for (i <- -5 until 0) assertResult(false)(vector.isDefinedAt(i))
        }
        s"return false with any index greater or equals to $n when isDefinedAt is invoked" in {
            for (i <- n until n + 5) assertResult(false)(vector.isDefinedAt(i))
        }
        "return a vector with exactly one additional element when :+ is invoked" in {
            def v = vector :+ element(42)
            assertResult(n + 1)(v.length)
        }
        "return a vector with exactly one additional element when +: is invoked" in {
            def v = element(42) +: vector
            assertResult(n + 1)(v.length)
        }
    }

}

class IntVectorSpec extends BaseVectorSpec[Int] {
    def element(n: Int) = n
}

class StringVectorSpec extends BaseVectorSpec[String] {
    def element(n: Int) = n.toString
}