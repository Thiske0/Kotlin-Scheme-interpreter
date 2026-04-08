package syntax

import com.github.h0tk3y.betterParse.combinators.map
import com.github.h0tk3y.betterParse.combinators.and
import com.github.h0tk3y.betterParse.combinators.optional
import com.github.h0tk3y.betterParse.combinators.separatedTerms
import com.github.h0tk3y.betterParse.combinators.unaryMinus
import com.github.h0tk3y.betterParse.grammar.Grammar

data class SchemeProgram(val entries: List<SchemeExpr>)

class SchemeProgramGrammar : Grammar<SchemeProgram>() {
    override val tokens = Tokens.toList()

    override val rootParser by
        (-optional(Tokens.ws) and
            separatedTerms(SchemeExprGrammar(), Tokens.ws, true)
            and -optional(Tokens.ws))
        .map { SchemeProgram(it) }
}