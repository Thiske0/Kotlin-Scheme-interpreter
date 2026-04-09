package syntax

import com.github.h0tk3y.betterParse.grammar.parseToEnd
import com.github.h0tk3y.betterParse.parser.ParseException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class SchemeKeywordGrammarTest {

    private val grammar = SchemeKeywordGrammar()

    @Test
    fun `parses if keyword correctly`() {
        val input = "if"
        val result = grammar.parseToEnd(input)
        assertEquals(SchemeKeyword.IF, result)
    }

    @Test
    fun `parses define keyword correctly`() {
        val input = "define"
        val result = grammar.parseToEnd(input)
        assertEquals(SchemeKeyword.DEFINE, result)
    }

    @Test
    fun `throws error for invalid keyword`() {
        val input = "maybe"
        assertThrows<ParseException> {
            grammar.parseToEnd(input)
        }
    }
}