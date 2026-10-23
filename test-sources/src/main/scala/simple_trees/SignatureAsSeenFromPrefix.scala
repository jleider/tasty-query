package simple_trees

/* References to the members of a generic class from another top-level class, through a prefix that instantiates the
 * class's type parameters. dotc pickles them as TERMREFs whose signature is the member's signature *as seen from the
 * prefix*, which differs from its declared signature.
 */

class SignatureAsSeenFromPrefixBase[T <: CharSequence]:
  def bounded(x: T): T = x

  def overloaded(x: T): T = x
  def overloaded(x: Any)(using DummyImplicit): T = null.asInstanceOf[T]
end SignatureAsSeenFromPrefixBase

// In an object, a static owner, unqualified references to inherited members stay `Ident`s with a `ThisType` prefix
object SignatureAsSeenFromPrefixObject extends SignatureAsSeenFromPrefixBase[String]:
  def callBounded(): String = bounded("a")
  def callOverloadedT(): String = overloaded("a")
  def callOverloadedAny(): String = overloaded(1)
end SignatureAsSeenFromPrefixObject

trait SignatureAsSeenFromPrefixCompanion[TC[_]]:
  given inst[A]: TC[A] = null.asInstanceOf[TC[A]]

trait SignatureAsSeenFromPrefixShow[A]

// A given that a companion object inherits is referenced as an `Ident` with the companion's module as prefix
object SignatureAsSeenFromPrefixShow extends SignatureAsSeenFromPrefixCompanion[SignatureAsSeenFromPrefixShow]

class SignatureAsSeenFromPrefixUser:
  def needsShow(using s: SignatureAsSeenFromPrefixShow[Int]): SignatureAsSeenFromPrefixShow[Int] = s
  def callNeedsShow(): SignatureAsSeenFromPrefixShow[Int] = needsShow
end SignatureAsSeenFromPrefixUser
