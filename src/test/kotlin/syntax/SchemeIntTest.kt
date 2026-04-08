package syntax

import com.github.h0tk3y.betterParse.grammar.parseToEnd
import com.github.h0tk3y.betterParse.parser.ParseException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class SchemeIntGrammarTest {

    private val grammar = SchemeIntGrammar()

    @Test
    fun `parses 0 correctly`() {
        val input = "0"
        val result = grammar.parseToEnd(input)
        assertEquals(SchemeInt(0), result)
    }

    @Test
    fun `parses -0 correctly`() {
        val input = "-0"
        val result = grammar.parseToEnd(input)
        assertEquals(SchemeInt(0), result)
    }

    @Test
    fun `parses positive numbers correctly`() {
        var input = "5"
        var result = grammar.parseToEnd(input)
        assertEquals(SchemeInt(5), result)
        input = "0123456789"
        result = grammar.parseToEnd(input)
        assertEquals(SchemeInt(123456789), result)
    }

    @Test
    fun `parses negative numbers correctly`() {
        var input = "-9"
        var result = grammar.parseToEnd(input)
        assertEquals(SchemeInt(-9), result)
        input = "-987643210"
        result = grammar.parseToEnd(input)
        assertEquals(SchemeInt(-987643210), result)
    }

    @Test
    fun `throws error for floats`() {
        val input = "1.0"
        assertThrows<ParseException> {
            grammar.parseToEnd(input)
        }
    }

    @Test
    fun `throws error for invalid token`() {
        val input = "#t"
        assertThrows<ParseException> {
            grammar.parseToEnd(input)
        }
    }
}