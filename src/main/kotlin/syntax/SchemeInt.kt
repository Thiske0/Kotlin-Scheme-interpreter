package syntax

import com.github.h0tk3y.betterParse.combinators.map
import com.github.h0tk3y.betterParse.grammar.Grammar

data class SchemeInt(val value: Int)

class SchemeIntGrammar : Grammar<SchemeInt>() {
    override val tokens = Tokens.toList()

    override val rootParser by
        Tokens.number map { SchemeInt(it.text.toInt()) }

}