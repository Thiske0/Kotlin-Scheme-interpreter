import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.io.path.absolutePathString
import kotlin.io.path.createTempFile
import kotlin.io.path.writeText
import kotlin.test.assertEquals

class MainTest {
    @Test
    fun `parses simple file correctly`() {
        val tempFile = createTempFile()
        tempFile.writeText("(define (f) 5)")
        val outputStream = ByteArrayOutputStream()
        val originalOut = System.out
        System.setOut(PrintStream(outputStream))

        try {
            main(arrayOf(tempFile.absolutePathString()))
        } finally {
            System.setOut(originalOut)
        }
        assertEquals("0\r\n", outputStream.toString())
    }
    @Test
    fun `parses complex file correctly`() {
        val tempFile = createTempFile()
        tempFile.writeText("(define (f x)\n" +
                "  (if x\n" +
                "      (f 1 (f 4))\n" +
                "      (if x\n" +
                "          (f 1)\n" +
                "          4)))")
        val outputStream = ByteArrayOutputStream()
        val originalOut = System.out
        System.setOut(PrintStream(outputStream))

        try {
            main(arrayOf(tempFile.absolutePathString()))
        } finally {
            System.setOut(originalOut)
        }
        assertEquals("2\r\n", outputStream.toString())
    }

    @Test
    fun `prints help when no arguments are provided`() {
        val outputStream = ByteArrayOutputStream()
        val originalOut = System.out
        System.setOut(PrintStream(outputStream))

        try {
            main(arrayOf())
        } finally {
            System.setOut(originalOut)
        }
        assertEquals("Please provide the file path of the file to count the amount of tail recursions.\r\n", outputStream.toString())
    }

    @Test
    fun `prints help when too many arguments are provided`() {
        val outputStream = ByteArrayOutputStream()
        val originalOut = System.out
        System.setOut(PrintStream(outputStream))

        try {
            main(arrayOf("a", "b"))
        } finally {
            System.setOut(originalOut)
        }
        assertEquals("Please provide the file path of the file to count the amount of tail recursions.\r\n", outputStream.toString())
    }

    @Test
    fun `prints error when too many expressions are present in the program`() {
        val tempFile = createTempFile()
        tempFile.writeText("(define (f) 5) (b)")
        val outputStream = ByteArrayOutputStream()
        val originalOut = System.out
        System.setOut(PrintStream(outputStream))

        try {
            main(arrayOf(tempFile.absolutePathString()))
        } finally {
            System.setOut(originalOut)
        }
        assertEquals("Program should contain exactly 1 expression.\r\n", outputStream.toString())
    }
}