import com.github.h0tk3y.betterParse.parser.ParseException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import syntax.SchemeExpr
import syntax.SchemeImmediate
import syntax.SchemeProgram
import syntax.SchemeTag
import java.io.FileNotFoundException
import kotlin.io.path.absolutePathString
import kotlin.io.path.writeText
import kotlin.test.assertEquals

class FileParserTest {
    @Test
    fun `parses simple file correctly`() {
        val tempFile = kotlin.io.path.createTempFile()
        tempFile.writeText("(a)")
        val program = FileParser.parseFile(tempFile.absolutePathString())
        assertEquals(SchemeProgram(listOf(SchemeExpr.List(listOf(SchemeExpr.Immediate(SchemeImmediate.Tag(SchemeTag.Variable("a"))))))), program)
    }

    @Test
    fun `throws error when file does not exist`() {
        assertThrows<FileNotFoundException> {
            FileParser.parseFile("nonexistent.file")
        }
    }

    @Test
    fun `throws error when file has invalid contents`() {
        val tempFile = kotlin.io.path.createTempFile()
        tempFile.writeText("(a")
        assertThrows<ParseException> {
            FileParser.parseFile(tempFile.absolutePathString())
        }
    }
}