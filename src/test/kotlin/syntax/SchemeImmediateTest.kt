package syntax

import com.github.h0tk3y.betterParse.grammar.parseToEnd
import com.github.h0tk3y.betterParse.parser.ParseException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class SchemeImmediateGrammarTest {

    private val grammar = SchemeImmediateGrammar()

    @Test
    fun `parses tag correctly`() {
        val input = "sdf"
        val result = grammar.parseToEnd(input)
        assertEquals(SchemeImmediate.Tag(SchemeTag.Variable("sdf")), result)
    }

    @Test
    fun `parses boolean token correctly`() {
        val input = "#t"
        val result = grammar.parseToEnd(input)
        assertEquals(SchemeImmediate.Bool(SchemeBool(true)), result)
    }

    @Test
    fun `parses singleton token correctly`() {
        val input = "'()"
        val result = grammar.parseToEnd(input)
        assertEquals(SchemeImmediate.Singleton(SchemeSingleton), result)
    }

    @Test
    fun `parses number token correctly`() {
        val input = "-9"
        val result = grammar.parseToEnd(input)
        assertEquals(SchemeImmediate.Int(SchemeInt(-9)), result)
    }

    @Test
    fun `parses string token correctly`() {
        val input = "\"\""
        val result = grammar.parseToEnd(input)
        assertEquals(SchemeImmediate.String(SchemeString("")), result)
    }

    @Test
    fun `throws error for empty token`() {
        val input = ""
        assertThrows<ParseException> {
            grammar.parseToEnd(input)
        }
    }

    @Test
    fun `rejects braces correctly`() {
        val input = "s)"
        assertThrows<ParseException> {
            grammar.parseToEnd(input)
        }
    }
}