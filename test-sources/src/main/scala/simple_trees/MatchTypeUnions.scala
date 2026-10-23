package simple_trees

/* Computing the signature of these methods reduces their parameter types, which requires proving that two types are
 * disjoint.
 */
abstract class MatchTypeUnions:
  class Method

  type UnionPattern[T] = T match
    case Int | String => Boolean
    case _            => String

  type ClassPattern[T] = T match
    case Method => Boolean
    case _      => Int

  type NothingPattern[T] = T match
    case Nothing => Boolean
    case _       => String

  def classScrutinee(x: UnionPattern[Method]): Unit = ()
  def unionScrutinee(x: ClassPattern[Int | String]): Unit = ()
  def intersectionScrutinee(x: ClassPattern[String & java.io.Serializable]): Unit = ()
  def nothingPattern(x: NothingPattern[Method]): Unit = ()

  // An intersection against an abstract type bounded by a union: dotc sees through the abstract type first
  class XC
  trait YT
  final class DD extends XC
  type Bounded <: Method | DD

  type AbstractPattern = (XC & YT) match
    case Bounded => Boolean
    case _       => String

  def abstractPattern(x: AbstractPattern): Unit = ()

  // An enum value is not provably disjoint from an abstract type, so these stay stuck
  type AbstractMember

  type EnumScrutinee = MatchTypeColor.Red.type match
    case AbstractMember => Boolean
    case _              => String

  type EnumScrutineeUnionPattern = MatchTypeColor.Red.type match
    case AbstractMember | MatchTypeColor.Green.type => Boolean
    case _                                          => String

  type EnumPattern = AbstractMember match
    case MatchTypeColor.Red.type => Boolean
    case _                       => String

  def enumScrutinee(x: EnumScrutinee): Unit = ()
  def enumScrutineeUnionPattern(x: EnumScrutineeUnionPattern): Unit = ()
  def enumPattern(x: EnumPattern): Unit = ()

  def calls(x1: EnumScrutinee, x2: EnumScrutineeUnionPattern, x3: EnumPattern): Unit =
    classScrutinee("a")
    unionScrutinee(1)
    intersectionScrutinee(2)
    nothingPattern("b")
    abstractPattern("c")
    enumScrutinee(x1)
    enumScrutineeUnionPattern(x2)
    enumPattern(x3)
  end calls
end MatchTypeUnions

enum MatchTypeColor:
  case Red, Green
