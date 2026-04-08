package syntax

import com.github.h0tk3y.betterParse.combinators.asJust
import com.github.h0tk3y.betterParse.grammar.Grammar

class SchemeSingleton

class SchemeSingletonGrammar : Grammar<SchemeSingleton>() {
    override val tokens = Tokens.toList()

    override val rootParser by
    Tokens.singleton asJust SchemeSingleton()

}