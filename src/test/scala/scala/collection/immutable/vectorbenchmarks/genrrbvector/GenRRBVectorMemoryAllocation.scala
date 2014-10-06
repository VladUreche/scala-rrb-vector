package scala.collection.immutable.vectorbenchmarks.genrrbvector

import scala.collection.immutable.vectorbenchmarks.GenRRBVectorBenchmark
import scala.collection.immutable.vectorbenchmarks.genericbenchmarks.MemoryAllocation
import scala.collection.immutable.vectorutils.VectorGeneratorType


abstract class GenRRBVectorAbstractMemoryAllocation[A] extends MemoryAllocation[A] with GenRRBVectorBenchmark[A]

class GenRRBVectorIntMemoryAllocation extends GenRRBVectorAbstractMemoryAllocation[Int] with VectorGeneratorType.IntGenerator

class GenRRBVectorAnyRefMemoryAllocation extends GenRRBVectorAbstractMemoryAllocation[AnyRef] with VectorGeneratorType.AnyRefGenerator