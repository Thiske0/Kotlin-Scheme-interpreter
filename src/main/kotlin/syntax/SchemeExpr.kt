package syntax

import com.github.h0tk3y.betterParse.combinators.map
import com.github.h0tk3y.betterParse.combinators.or
import com.github.h0tk3y.betterParse.combinators.and
import com.github.h0tk3y.betterParse.combinators.optional
import com.github.h0tk3y.betterParse.combinators.separatedTerms
import com.github.h0tk3y.betterParse.combinators.unaryMinus
import com.github.h0tk3y.betterParse.grammar.Grammar
import com.github.h0tk3y.betterParse.grammar.parser
import com.github.h0tk3y.betterParse.parser.Parser

sealed class SchemeExpr {
    data class Immediate(val value: SchemeImmediate) : SchemeExpr()
    data class List(val entries: kotlin.collections.List<SchemeExpr>) : SchemeExpr()
}

class SchemeExprGrammar : Grammar<SchemeExpr>() {
    override val tokens = Tokens.toList()

    val immediate by SchemeImmediateGrammar() map { SchemeExpr.Immediate(it) }
    val list: Parser<SchemeExpr> by parser(this::listParser)

    val listParser by
        (-Tokens.lpar and -optional(Tokens.ws) and
                separatedTerms(immediate or list, Tokens.ws)
            and -optional(Tokens.ws) and -Tokens.rpar)
        .map { SchemeExpr.List(it) }

    override val rootParser by list
}