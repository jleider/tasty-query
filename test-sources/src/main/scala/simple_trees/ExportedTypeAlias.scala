package simple_trees

object ExportedTypeAliasLib:
  class Box[A]
  class Plain
  type Alias[A] = List[A]
end ExportedTypeAliasLib

// `export` generates a type alias for each exported type
object ExportedTypeAliasPredef:
  export ExportedTypeAliasLib.{Alias, Box, Plain}

trait ExportedTypeAliasParent:
  def generic[B](box: ExportedTypeAliasLib.Box[B]): ExportedTypeAliasLib.Box[B]
  def plain(p: ExportedTypeAliasLib.Plain): ExportedTypeAliasLib.Plain
  def alias[B](xs: List[B]): List[B]
end ExportedTypeAliasParent

// Overrides whose parameter types are written with the exported type aliases
class ExportedTypeAliasChild extends ExportedTypeAliasParent:
  def generic[B](box: ExportedTypeAliasPredef.Box[B]): ExportedTypeAliasPredef.Box[B] = box
  def plain(p: ExportedTypeAliasPredef.Plain): ExportedTypeAliasPredef.Plain = p
  def alias[B](xs: ExportedTypeAliasPredef.Alias[B]): ExportedTypeAliasPredef.Alias[B] = xs
end ExportedTypeAliasChild
