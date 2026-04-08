import com.github.h0tk3y.betterParse.grammar.parseToEnd
import syntax.SchemeProgramGrammar

fun main() {
    val input = "(f 2 3 (df ) )  "
    val parser = SchemeProgramGrammar()
    val program = parser.parseToEnd(input)
    println(program)
}