package syntax

import com.github.h0tk3y.betterParse.combinators.map
import com.github.h0tk3y.betterParse.grammar.Grammar
import com.github.h0tk3y.betterParse.grammar.tryParseToEnd
import com.github.h0tk3y.betterParse.parser.ErrorResult
import com.github.h0tk3y.betterParse.parser.Parsed

sealed class SchemeTag {
    data class Keyword(val keyword: SchemeKeyword) : SchemeTag()
    data class Variable(val name: String) : SchemeTag()

    companion object {
        fun from(name: String): SchemeTag {
            val keyword = SchemeKeywordGrammar().tryParseToEnd(name)
            return if (keyword is ErrorResult) Variable(name) else Keyword((keyword as Parsed).value)
        }
    }
}

class SchemeTagGrammar : Grammar<SchemeTag>() {
    override val tokens = Tokens.toList()

    override val rootParser by
        Tokens.tag map { SchemeTag.from(it.text) }
}