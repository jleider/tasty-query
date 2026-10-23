package simple_trees

final case class MatchTypeMemberResult(field: Int)

class MatchTypeMemberBuilder[-Origin, +A]

/* Selecting a member of a value whose type is a match type requires looking up the member in its reduction. */
class MatchTypeMemberSelection:
  type Infer[S] = S match
    case MatchTypeMemberBuilder[?, a] => a

  type Result = Infer[MatchTypeMemberBuilder[Any, MatchTypeMemberResult]]

  def select(x: Result): Int = x.field

  def selectInLambda(xs: List[Result]): List[Int] = xs.map(_.field)
end MatchTypeMemberSelection
