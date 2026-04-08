package syntax

import com.github.h0tk3y.betterParse.grammar.parseToEnd
import com.github.h0tk3y.betterParse.parser.ParseException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class SchemeStringGrammarTest {

    private val grammar = SchemeStringGrammar()

    @Test
    fun `parses empty string correctly`() {
        val input = "\"\""
        val result = grammar.parseToEnd(input)
        assertEquals(SchemeString(""), result)
    }

    @Test
    fun `parses simple string correctly`() {
        var input = "\"abc\""
        var result = grammar.parseToEnd(input)
        assertEquals(SchemeString("abc"), result)
        input = "\"9\""
        result = grammar.parseToEnd(input)
        assertEquals(SchemeString("9"), result)
    }

    @Test
    fun `handles escaping correctly`() {
        val input = "\"\\n\\r\\\"\\\'\\\\\\\"\\\\\""
        val result = grammar.parseToEnd(input)
        assertEquals(SchemeString("\n\r\"\'\\\"\\"), result)
    }

    @Test
    fun `throws error for invalid token`() {
        val input = "#t"
        assertThrows<ParseException> {
            grammar.parseToEnd(input)
        }
    }

    @Test
    fun `handles invalid escape sequence gracefully`() {
        val input = "\"\\q\""
        val result = grammar.parseToEnd(input)
        assertEquals(SchemeString("q"), result)
    }
}