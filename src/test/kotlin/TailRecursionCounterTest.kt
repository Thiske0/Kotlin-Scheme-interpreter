import com.github.h0tk3y.betterParse.grammar.parseToEnd
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import syntax.SchemeExprGrammar
import kotlin.test.assertEquals

class TailRecursionCounterTest {
    @Test
    fun `counts empty function correctly`() {
        val input = "(define (f))"
        val parsedInput = SchemeExprGrammar().parseToEnd(input)
        val result = TailRecursionCounter.countTailRecursion(parsedInput)
        assertEquals(0, result)
    }

    @Test
    fun `counts simple function correctly`() {
        var input = "(define (f) (f) (f))"
        var parsedInput = SchemeExprGrammar().parseToEnd(input)
        var result = TailRecursionCounter.countTailRecursion(parsedInput)
        assertEquals(1, result)
        input = "(define (f) (f) 5)"
        parsedInput = SchemeExprGrammar().parseToEnd(input)
        result = TailRecursionCounter.countTailRecursion(parsedInput)
        assertEquals(0, result)
        input = "(define (f) (f) (b))"
        parsedInput = SchemeExprGrammar().parseToEnd(input)
        result = TailRecursionCounter.countTailRecursion(parsedInput)
        assertEquals(0, result)
        input = "(define (f) (f) (f 4))"
        parsedInput = SchemeExprGrammar().parseToEnd(input)
        result = TailRecursionCounter.countTailRecursion(parsedInput)
        assertEquals(0, result)
        input = "(define (f) (f) ((1 2) 5))"
        parsedInput = SchemeExprGrammar().parseToEnd(input)
        result = TailRecursionCounter.countTailRecursion(parsedInput)
        assertEquals(0, result)
    }

    @Test
    fun `throws on invalid function declaration`() {
        var input = "(if (f) (f) (f))"
        var parsedInput = SchemeExprGrammar().parseToEnd(input)
        assertThrows<IllegalArgumentException> {
            TailRecursionCounter.countTailRecursion(parsedInput)
        }
        input = "(5 (f) (f) (f))"
        parsedInput = SchemeExprGrammar().parseToEnd(input)
        assertThrows<IllegalArgumentException> {
            TailRecursionCounter.countTailRecursion(parsedInput)
        }
        input = "((d) (f) (f) (f))"
        parsedInput = SchemeExprGrammar().parseToEnd(input)
        assertThrows<IllegalArgumentException> {
            TailRecursionCounter.countTailRecursion(parsedInput)
        }
        input = "(define)"
        parsedInput = SchemeExprGrammar().parseToEnd(input)
        assertThrows<IllegalArgumentException> {
            TailRecursionCounter.countTailRecursion(parsedInput)
        }
    }

    @Test
    fun `throws on invalid function header`() {
        var input = "(define 5 (f) (f))"
        var parsedInput = SchemeExprGrammar().parseToEnd(input)
        assertThrows<IllegalArgumentException> {
            TailRecursionCounter.countTailRecursion(parsedInput)
        }
        input = "(define (5) (f) (f))"
        parsedInput = SchemeExprGrammar().parseToEnd(input)
        assertThrows<IllegalArgumentException> {
            TailRecursionCounter.countTailRecursion(parsedInput)
        }
        input = "(define ((f)) (f) (f))"
        parsedInput = SchemeExprGrammar().parseToEnd(input)
        assertThrows<IllegalArgumentException> {
            TailRecursionCounter.countTailRecursion(parsedInput)
        }
    }

    @Test
    fun `handles if statements correctly`() {
        var input = "(define (f) (if 0 (f) (f)))"
        var parsedInput = SchemeExprGrammar().parseToEnd(input)
        var result = TailRecursionCounter.countTailRecursion(parsedInput)
        assertEquals(2, result)
        input = "(define (f) (if 0 (f)))"
        parsedInput = SchemeExprGrammar().parseToEnd(input)
        result = TailRecursionCounter.countTailRecursion(parsedInput)
        assertEquals(1, result)
        input = "(define (f) (if 0 (f) 4 (f)))"
        parsedInput = SchemeExprGrammar().parseToEnd(input)
        result = TailRecursionCounter.countTailRecursion(parsedInput)
        assertEquals(2, result)
        input = "(define (f) (if 0 (f) 4 (4)))"
        parsedInput = SchemeExprGrammar().parseToEnd(input)
        result = TailRecursionCounter.countTailRecursion(parsedInput)
        assertEquals(1, result)
        input = "(define (f) (if 0 (f) 4 ((1 2) 4)))"
        parsedInput = SchemeExprGrammar().parseToEnd(input)
        result = TailRecursionCounter.countTailRecursion(parsedInput)
        assertEquals(1, result)
        input = "(define (f) (if 0))"
        parsedInput = SchemeExprGrammar().parseToEnd(input)
        assertThrows<IllegalArgumentException> {
            TailRecursionCounter.countTailRecursion(parsedInput)
        }
    }

    @Test
    fun `handles nested if statements correctly`() {
        val input = "(define (f) (if 0 (if 0 (f) (f)) (if 0 (f) (f))))"
        val parsedInput = SchemeExprGrammar().parseToEnd(input)
        val result = TailRecursionCounter.countTailRecursion(parsedInput)
        assertEquals(4, result)
    }

    @Test
    fun `handles name overwrites correctly`() {
        var input = "(define (if a b c) (if 0 (if 0 (a) (b)) (if 0 (a) (b))))"
        var parsedInput = SchemeExprGrammar().parseToEnd(input)
        var result = TailRecursionCounter.countTailRecursion(parsedInput)
        assertEquals(1, result)
        input = "(define (if a b c d) (if 0 (if 0 (a) (b) (c)) (if 0 (a) (b) (c))))"
        parsedInput = SchemeExprGrammar().parseToEnd(input)
        result = TailRecursionCounter.countTailRecursion(parsedInput)
        assertEquals(2, result)
    }
}