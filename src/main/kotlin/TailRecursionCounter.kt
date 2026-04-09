import syntax.SchemeExpr
import syntax.SchemeImmediate
import syntax.SchemeKeyword
import syntax.SchemeTag

object TailRecursionCounter {
    fun countTailRecursion(expr: SchemeExpr): Int {
        // verify it is afunction declaration
        val contents = expr as SchemeExpr.List
        if (contents.entries[0] != SchemeExpr.Immediate(SchemeImmediate.Tag(SchemeTag.Keyword(SchemeKeyword.DEFINE))))
            throw IllegalArgumentException("Expected expression to be a function definition")
        if (contents.entries.size == 1)
            throw IllegalArgumentException("function definition needs at least a header")
        // extract function name and number of args from function header
        val header = contents.entries[1]
        if (header !is SchemeExpr.List) throw IllegalArgumentException("function definition needs a valid header")
        val numberOfArguments = header.entries.size - 1
        val functionName = header.entries[0]
        if (functionName !is SchemeExpr.Immediate) throw IllegalArgumentException("function definition needs a valid function name")
        if (functionName.value !is SchemeImmediate.Tag) throw IllegalArgumentException("function definition needs a valid function name")
        if (contents.entries.size == 2)
            // empty body so no tail recursion
            return 0
        // only the final expression in the function body can the tail call optimized
        return countTailRecursionInner(contents.entries.last(), functionName.value.value, numberOfArguments)
    }

    private fun countTailRecursionInner(expr: SchemeExpr, functionName: SchemeTag, numberOfArguments: Int): Int {
        // An immediate is not a function call so cannot be optimized
        val contents = expr as? SchemeExpr.List ?: return 0
        val first = contents.entries.first()
        // For it to be possible to optimize it has to be a tag
        if (first !is SchemeExpr.Immediate) return 0
        val firstTag = first.value as? SchemeImmediate.Tag ?: return 0
        // if the tag matches and number of arguments is correct, it can be optimized
        if (firstTag.value == functionName && contents.entries.size - 1 == numberOfArguments)
            return 1
        // if it's not an if statement, we are done
        if (firstTag != SchemeImmediate.Tag(SchemeTag.Keyword(SchemeKeyword.IF)))
            return 0
        // an if should contain at least 3 elements namely (if predicate if_true)
        if (contents.entries.size < 3)
            throw IllegalArgumentException("Invalid if statement")
        // check for tail recursion in the true branch
        var count = countTailRecursionInner(contents.entries[2], functionName, numberOfArguments)
        // check if false branch exists
        if (contents.entries.size > 3) {
            // tail call optimization only possible for final expression.
            count += countTailRecursionInner(contents.entries.last(), functionName, numberOfArguments)
        }
        return count
    }
}