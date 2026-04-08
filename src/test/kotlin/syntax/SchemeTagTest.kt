package syntax

import com.github.h0tk3y.betterParse.grammar.parseToEnd
import com.github.h0tk3y.betterParse.parser.ParseException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class SchemeTagGrammarTest {

    private val grammar = SchemeTagGrammar()

    @Test
    fun `parses simple tag correctly`() {
        val input = "sdf"
        val result = grammar.parseToEnd(input)
        assertEquals(SchemeTag("sdf"), result)
    }

    @Test
    fun `parses complex tag correctly`() {
        val input = "fse4sfe./#sdf_9"
        val result = grammar.parseToEnd(input)
        assertEquals(SchemeTag("fse4sfe./#sdf_9"), result)
    }

    @Test
    fun `rejects braces correctly`() {
        val input = "s)"
        assertThrows<ParseException> {
            grammar.parseToEnd(input)
        }
    }

    @Test
    fun `throws error for boolean token`() {
        val input = "#t"
        assertThrows<ParseException> {
            grammar.parseToEnd(input)
        }
    }

    @Test
    fun `throws error for singleton token`() {
        val input = "'()"
        assertThrows<ParseException> {
            grammar.parseToEnd(input)
        }
    }

    @Test
    fun `throws error for number token`() {
        val input = "-9"
        assertThrows<ParseException> {
            grammar.parseToEnd(input)
        }
    }

    @Test
    fun `throws error for string token`() {
        val input = "\"\""
        assertThrows<ParseException> {
            grammar.parseToEnd(input)
        }
    }

    @Test
    fun `throws error for empty tag`() {
        val input = ""
        assertThrows<ParseException> {
            grammar.parseToEnd(input)
        }
    }
}