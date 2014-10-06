package scala.collection.immutable.vectorbenchmarks.rrbvector

import scala.collection.immutable.vectorbenchmarks.genericbenchmarks.MemoryAllocation
import scala.collection.immutable.vectorbenchmarks.RRBVectorBenchmark
import scala.collection.immutable.vectorutils.VectorGeneratorType


abstract class RRBVectorAbstractMemoryAllocation[A] extends MemoryAllocation[A] with RRBVectorBenchmark[A]

class RRBVectorIntMemoryAllocation extends RRBVectorAbstractMemoryAllocation[Int] with VectorGeneratorType.IntGenerator

class RRBVectorAnyRefMemoryAllocation extends RRBVectorAbstractMemoryAllocation[AnyRef] with VectorGeneratorType.AnyRefGenerator