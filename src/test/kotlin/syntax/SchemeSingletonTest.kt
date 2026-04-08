package syntax

import com.github.h0tk3y.betterParse.grammar.parseToEnd
import com.github.h0tk3y.betterParse.parser.ParseException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class SchemeSingletonGrammarTest {

    private val grammar = SchemeSingletonGrammar()

    @Test
    fun `parses singleton token correctly`() {
        val input = "'()"
        val result = grammar.parseToEnd(input)
        assertEquals(SchemeSingleton, result)
    }

    @Test
    fun `throws error for invalid token`() {
        val input = "0"
        assertThrows<ParseException> {
            grammar.parseToEnd(input)
        }
    }
}