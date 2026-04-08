package syntax

import com.github.h0tk3y.betterParse.lexer.Token
import com.github.h0tk3y.betterParse.lexer.literalToken
import com.github.h0tk3y.betterParse.lexer.regexToken
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible


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


    // We use reflection to turn all member fields that are Tokens into a List
    fun toList(): List<Token> {
        return this::class.declaredMemberProperties
            .mapNotNull { prop ->
                prop.isAccessible = true
                val value = prop.getter.call(this)
                value as? Token // safe cast
            }
    }
}