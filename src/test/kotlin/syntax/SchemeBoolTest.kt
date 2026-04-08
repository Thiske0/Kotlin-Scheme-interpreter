package syntax

import com.github.h0tk3y.betterParse.grammar.parseToEnd
import com.github.h0tk3y.betterParse.parser.ParseException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class SchemeBoolGrammarTest {

    private val grammar = SchemeBoolGrammar()

    @Test
    fun `parses true token correctly`() {
        val input = "#t"
        val result = grammar.parseToEnd(input)
        assertEquals(SchemeBool(true), result)
    }

    @Test
    fun `parses false token correctly`() {
        val input = "#f"
        val result = grammar.parseToEnd(input)
        assertEquals(SchemeBool(false), result)
    }

    @Test
    fun `throws error for invalid token`() {
        val input = "maybe"
        assertThrows<ParseException> {
            grammar.parseToEnd(input)
        }
    }
}