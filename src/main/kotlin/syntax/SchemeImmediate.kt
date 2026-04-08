package syntax

import com.github.h0tk3y.betterParse.combinators.map
import com.github.h0tk3y.betterParse.combinators.or
import com.github.h0tk3y.betterParse.grammar.Grammar

sealed class SchemeImmediate {
    data class Singleton(val value: SchemeSingleton) : SchemeImmediate()
    data class Bool(val value: SchemeBool) : SchemeImmediate()
    data class Int(val value: SchemeInt) : SchemeImmediate()
    data class String(val value: SchemeString) : SchemeImmediate()
    data class Tag(val value: SchemeTag) : SchemeImmediate()
}

class SchemeImmediateGrammar : Grammar<SchemeImmediate>() {
    override val tokens = Tokens.toList()

    override val rootParser by
        (SchemeSingletonGrammar()   map { SchemeImmediate.Singleton(it)}) or
        (SchemeBoolGrammar()        map { SchemeImmediate.Bool(it) }) or
        (SchemeIntGrammar()         map { SchemeImmediate.Int(it)  }) or
        (SchemeStringGrammar()      map { SchemeImmediate.String(it)  }) or
        (SchemeTagGrammar()         map { SchemeImmediate.Tag(it)  })
}