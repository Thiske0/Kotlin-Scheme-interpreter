package syntax

import com.github.h0tk3y.betterParse.combinators.asJust
import com.github.h0tk3y.betterParse.combinators.or
import com.github.h0tk3y.betterParse.grammar.Grammar
import com.github.h0tk3y.betterParse.lexer.literalToken

enum class SchemeKeyword {
    IF, DEFINE
}

class SchemeKeywordGrammar : Grammar<SchemeKeyword>() {
    val ifToken by literalToken("if")
    val defineToken by literalToken("define")

    override val rootParser by
    (ifToken asJust SchemeKeyword.IF) or (defineToken asJust SchemeKeyword.DEFINE)

}