package syntax

import com.github.h0tk3y.betterParse.combinators.map
import com.github.h0tk3y.betterParse.grammar.Grammar

data class SchemeTag(val value: String)

class SchemeTagGrammar : Grammar<SchemeTag>() {
    override val tokens = Tokens.toList()

    override val rootParser by
        Tokens.tag map { SchemeTag(it.text) }
}