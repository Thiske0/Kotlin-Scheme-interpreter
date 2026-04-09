import syntax.SchemeExpr
import syntax.SchemeImmediate
import syntax.SchemeKeyword
import syntax.SchemeTag

object TailRecursionCounter {
    fun countTailRecursion(expr: SchemeExpr): Int {
        val contents = expr as SchemeExpr.List
        if (contents.entries[0] != SchemeExpr.Immediate(SchemeImmediate.Tag(SchemeTag.Keyword(SchemeKeyword.DEFINE))))
            throw IllegalArgumentException("Expected expression to be a function definition")
        if (contents.entries.size == 1)
            throw IllegalArgumentException("function definition needs at least a header")
        if (contents.entries.size == 2)
            // empty body so no tail recursion
            return 0
        // only the final expression in the function body can the tail call optimized
        return countTailRecursionInner(contents.entries.last())
    }

    private fun countTailRecursionInner(expr: SchemeExpr): Int {
        // An immediate is not a function call so cannot be optimized
        val contents = expr as? SchemeExpr.List ?: return 0
        val first = contents.entries.first()
        // For it to be possible to optimize it has to be a tag
        if (first !is SchemeExpr.Immediate) return 0
        val firstTag = first.value as? SchemeImmediate.Tag ?: return 0
        // if the tag is an if, more work has to be done, otherwise it can be optimised
        if (firstTag != SchemeImmediate.Tag(SchemeTag.Keyword(SchemeKeyword.IF)))
            return 1
        // an if should contain at least 3 elements namely (if predicate if_true)
        if (contents.entries.size < 3)
            throw IllegalArgumentException("Invalid if statement")
        // check for tail recursion in the true branch
        var count = countTailRecursionInner(contents.entries[2])
        // check if false branch exists
        if (contents.entries.size > 3) {
            // tail call optimization only possible for final expression.
            count += countTailRecursionInner(contents.entries.last())
        }
        return count
    }
}