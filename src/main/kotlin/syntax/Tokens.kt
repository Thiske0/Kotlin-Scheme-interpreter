package syntax

import com.github.h0tk3y.betterParse.lexer.Token
import com.github.h0tk3y.betterParse.lexer.literalToken
import com.github.h0tk3y.betterParse.lexer.regexToken


object Tokens {
    // note: this doesn't follow the scheme spec exactly but is fine for a simple implementation

    val trueToken = literalToken("#t")
    val falseToken = literalToken("#f")
    val number = regexToken("-?\\d+")
    val singleton = literalToken("'()")
    val tag = regexToken("[^\\s()]+")
    val string = regexToken("\"([^\"\\\\]|\\\\.)*\"")
    val ws = regexToken("\\s+")
    val lpar = literalToken("(")
    val rpar = literalToken(")")

    fun toList(): List<Token> {
        //note: order matters for parsing reasons, most restrictive first
        return listOf(
            trueToken,
            falseToken,
            lpar,
            rpar,
            singleton,
            number,
            string,
            tag,
            ws,
        )
    }
}