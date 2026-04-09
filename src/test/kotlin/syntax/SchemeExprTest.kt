package syntax

import com.github.h0tk3y.betterParse.grammar.parseToEnd
import com.github.h0tk3y.betterParse.parser.ParseException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class SchemeExprGrammarTest {

    private val grammar = SchemeExprGrammar()

    @Test
    fun `parses simple expression correctly`() {
        val input = "(f)"
        val result = grammar.parseToEnd(input)
        assertEquals(SchemeExpr.List(listOf(SchemeExpr.Immediate(SchemeImmediate.Tag(SchemeTag.Variable("f"))))), result)
    }

    @Test
    fun `parses long expression correctly`() {
        var input = "(a b c)"
        var result = grammar.parseToEnd(input)
        assertEquals(SchemeExpr.List(listOf(SchemeExpr.Immediate(SchemeImmediate.Tag(SchemeTag.Variable("a"))), SchemeExpr.Immediate(SchemeImmediate.Tag(SchemeTag.Variable("b"))), SchemeExpr.Immediate(SchemeImmediate.Tag(SchemeTag.Variable("c"))))), result)
        input = "(\na \n \rb c\r\t )"
        result = grammar.parseToEnd(input)
        assertEquals(SchemeExpr.List(listOf(SchemeExpr.Immediate(SchemeImmediate.Tag(SchemeTag.Variable("a"))), SchemeExpr.Immediate(SchemeImmediate.Tag(SchemeTag.Variable("b"))), SchemeExpr.Immediate(SchemeImmediate.Tag(SchemeTag.Variable("c"))))), result)
    }

    @Test
    fun `parses nested expression correctly`() {
        var input = "(a (b) c)"
        var result = grammar.parseToEnd(input)
        assertEquals(SchemeExpr.List(listOf(SchemeExpr.Immediate(SchemeImmediate.Tag(SchemeTag.Variable("a"))), SchemeExpr.List(listOf(SchemeExpr.Immediate(SchemeImmediate.Tag(SchemeTag.Variable("b"))))), SchemeExpr.Immediate(SchemeImmediate.Tag(SchemeTag.Variable("c"))))), result)
        input = "((a b) c)"
        result = grammar.parseToEnd(input)
        assertEquals(SchemeExpr.List(listOf(SchemeExpr.List(listOf(SchemeExpr.Immediate(SchemeImmediate.Tag(SchemeTag.Variable("a"))), SchemeExpr.Immediate(SchemeImmediate.Tag(SchemeTag.Variable("b"))))), SchemeExpr.Immediate(SchemeImmediate.Tag(SchemeTag.Variable("c"))))), result)
        input = "(a (b c))"
        result = grammar.parseToEnd(input)
        assertEquals(SchemeExpr.List(listOf(SchemeExpr.Immediate(SchemeImmediate.Tag(SchemeTag.Variable("a"))), SchemeExpr.List(listOf(SchemeExpr.Immediate(SchemeImmediate.Tag(SchemeTag.Variable("b"))), SchemeExpr.Immediate(SchemeImmediate.Tag(SchemeTag.Variable("c"))))))), result)
    }

    @Test
    fun `throws error for empty expression`() {
        var input = ""
        assertThrows<ParseException> {
            grammar.parseToEnd(input)
        }
        input = "()"
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

    @Test
    fun `throws error when expression does not start and end with braces`() {
        var input = "(c) "
        assertThrows<ParseException> {
            grammar.parseToEnd(input)
        }
        input = " (c)"
        assertThrows<ParseException> {
            grammar.parseToEnd(input)
        }
    }
}