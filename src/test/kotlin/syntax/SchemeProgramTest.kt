package syntax

import com.github.h0tk3y.betterParse.grammar.parseToEnd
import com.github.h0tk3y.betterParse.parser.ParseException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class SchemeProgramGrammarTest {

    private val grammar = SchemeProgramGrammar()

    fun makeSimpleExpression(tag: String): SchemeExpr {
        return SchemeExpr.List(listOf(SchemeExpr.Immediate(SchemeImmediate.Tag(SchemeTag(tag)))))
    }

    @Test
    fun `parses empty program correctly`() {
        var input = ""
        var result = grammar.parseToEnd(input)
        assertEquals(SchemeProgram(listOf()), result)
        input = " \n"
        result = grammar.parseToEnd(input)
        assertEquals(SchemeProgram(listOf()), result)
    }

    @Test
    fun `parses simple program correctly`() {
        val input = "(f)"
        val result = grammar.parseToEnd(input)
        assertEquals(SchemeProgram(listOf(makeSimpleExpression("f"))), result)
    }

    @Test
    fun `parses longer program correctly`() {
        val input = "(a) (b) (c)"
        val result = grammar.parseToEnd(input)
        assertEquals(SchemeProgram(listOf(makeSimpleExpression("a"), makeSimpleExpression("b"), makeSimpleExpression("c"))), result)
    }

    @Test
    fun `handles whitespace in begin and end correctly`() {
        val input = " (f)\n"
        val result = grammar.parseToEnd(input)
        assertEquals(SchemeProgram(listOf(makeSimpleExpression("f"))), result)
    }

    @Test
    fun `throws error when no whitespace in between expressions`() {
        val input = "(f)(g)"
        assertThrows<ParseException> {
            grammar.parseToEnd(input)
        }
    }

    @Test
    fun `throws error for mismatched braces`() {
        var input = "((a (b) c)"
        assertThrows<ParseException> {
            grammar.parseToEnd(input)
        }
        input = "(a (b)) c)"
        assertThrows<ParseException> {
            grammar.parseToEnd(input)
        }
    }
}