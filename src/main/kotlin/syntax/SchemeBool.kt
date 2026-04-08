package syntax

import com.github.h0tk3y.betterParse.combinators.asJust
import com.github.h0tk3y.betterParse.combinators.map
import com.github.h0tk3y.betterParse.combinators.or
import com.github.h0tk3y.betterParse.grammar.Grammar

class SchemeBool(val value: Boolean)

class SchemeBoolGrammar : Grammar<SchemeBool>() {
    override val tokens = Tokens.toList()

    override val rootParser by
        ((Tokens.trueToken asJust true) or (Tokens.falseToken asJust false))
            .map { SchemeBool(it) }

}