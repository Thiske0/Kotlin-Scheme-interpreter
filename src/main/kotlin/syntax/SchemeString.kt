package syntax

import com.github.h0tk3y.betterParse.combinators.map
import com.github.h0tk3y.betterParse.grammar.Grammar
import org.apache.commons.text.StringEscapeUtils

data class SchemeString(val value: String)

class SchemeStringGrammar : Grammar<SchemeString>() {

    override val tokens = Tokens.toList()
    override val rootParser by
        Tokens.string map { SchemeString(parseString(it.text)) }

}

fun parseString(text: String): String {
    // To unescape the string to handle things like "\n" "\r" etc
    // If invalid just removes the backslash
    return StringEscapeUtils.unescapeJava(text.removeSurrounding("\""))
}