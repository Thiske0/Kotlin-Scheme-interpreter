import com.github.h0tk3y.betterParse.grammar.parseToEnd
import syntax.SchemeProgram
import syntax.SchemeProgramGrammar
import java.io.File

object FileParser {
    fun parseFile(filepath: String): SchemeProgram {
        val contents = File(filepath).readText(Charsets.UTF_8)
        return SchemeProgramGrammar().parseToEnd(contents)
    }
}