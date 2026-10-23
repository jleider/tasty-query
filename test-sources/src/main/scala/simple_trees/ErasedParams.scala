package simple_trees

import scala.annotation.experimental
import scala.language.experimental.erasedDefinitions

// Values of a class that extends `compiletime.Erased` are erased, and so are parameters of that type
@experimental
trait ErasedEvidence extends compiletime.Erased

@experimental
object ErasedEvidence:
  given evidence: ErasedEvidence = new ErasedEvidence {}

/* dotc leaves erased parameters out of method signatures, both at definition and at call sites. */
@experimental
class ErasedParams:
  def explicitlyErased(x: Int)(using erased y: Int): Int = x
  def erasedByType(x: Int)(using ev: ErasedEvidence): Int = x
  def polyErasedByType[A](x: A)(using ev: ErasedEvidence): A = x

  // A function type with an erased parameter, which erases to Function1
  def erasedFunction(f: (x: Int, erased y: Int) => Int): Int = f(1, 2)

  def calls(): Int =
    explicitlyErased(1)(using 2)
    polyErasedByType(1)
    erasedByType(1)
    erasedFunction((x: Int, erased y: Int) => x)
  end calls

  // A refinement with an erased parameter, written as a type tree
  type Refined = ErasedParams { def explicitlyErased(x: Int)(using erased y: Int): Int }

  def selectOnRefined(r: Refined): Int = r.explicitlyErased(1)(using 2)

  // A refinement with an erased parameter in an inferred type, which is pickled in the types section
  def inferredRefined = null.asInstanceOf[ErasedParams { def refinedOnly(x: Int)(using erased y: Int): Int }]
end ErasedParams

// The same method as `ErasedParams.explicitlyErased`, but whose second parameter is not erased
class ErasedParamsNotErased:
  def explicitlyErased(x: Int)(using y: Int): Int = x
end ErasedParamsNotErased
