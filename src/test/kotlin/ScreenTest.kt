/*Created on 29/04/18. */
import junit.framework.TestCase.assertEquals
import org.junit.Test
import org.mockito.Mockito.*
import java.io.BufferedReader
import java.io.StringReader

class ScreenTest {
    val s = Screen(ColourPrinter())

    @Test
    fun initialisedBlank() {
        assertEquals("", s.toString())
    }

    @Test
    fun canShowQuestion() {
        val q = Question("What is 2*2?", "4")

        s.showQuestion(q)

        assertEquals("What is 2*2?", s.toString())
    }

    @Test(timeout = 700)
    fun canAwaitAnswer() {
        val mockBufferedReader = mock(BufferedReader::class.java)
        doReturn("4").`when`(mockBufferedReader).readLine()

        assertEquals("4", s.awaitAnswer(mockBufferedReader).toString())
    }

    @Test
    fun canAppendAnswerText() {
        val q = Question("Translate 'thanks'", "გმადლობ")

        s.showQuestion(q)
        s.showAnswer("გმადლომ")

        assertEquals("Translate 'thanks'\nგმადლომ", s.toString())
    }

    @Test
    fun correctAnswerCanBeTurnedGreen() {
        val q = Question("Translate 'thanks'", "გმადლობ")

        s.showQuestion(q)
        s.showAnswer("გმადლობ")
        s.showAnswerCorrect()

        assertEquals(Colour.W, s.lines[0].second.baseColour)
        assertEquals(Colour.G, s.lines[1].second.baseColour)
    }

    @Test
    fun incorrectAnswerCanBeTurnedRed() {
        val q = Question("Translate 'thanks'", "გმადლობ")

        s.showQuestion(q)
        s.showAnswer("ayylmao")
        s.showAnswerEntirelyIncorrect()

        assertEquals(Colour.W, s.lines[0].second.baseColour)
        assertEquals(Colour.R, s.lines[1].second.baseColour)
    }

    @Test
    fun incorrectIndicesCanBeTurnedRed() {
        val q = Question("Translate 'thanks'", "გმადლობ")

        s.showQuestion(q)
        s.showAnswer("გმადლომ")
        s.showAnswerIncorrectIndices(mutableSetOf(6))

        assertEquals(Colour.W, s.lines[0].second.baseColour)
        assertEquals(Colour.G, s.lines[1].second.baseColour)
        assertEquals(Colour.R, s.lines[1].second.overlayColour)
        assertEquals(mutableSetOf(6), s.lines[1].second.overlayIndices)
    }

    @Test
    fun canAnnotateAnswerWithCorrection() {
        val q = Question("Translate 'thanks'", "გმადლობ")

        s.showQuestion(q)
        s.showAnswer("გმადლომ")
        s.showAnswerIncorrectIndices(mutableSetOf(6))
        s.showCorrection(q, mutableSetOf(6))

        assertEquals("Translate 'thanks'\nგმადლომ\n      ბ\ncorrect answer: გმადლობ", s.toString())
    }

    @Test(timeout = 700)
    fun canAwaitCorrection() {
        val mockBufferedReader = mock(BufferedReader::class.java)
        doReturn("გმადლობ").`when`(mockBufferedReader).readLine()
        val q = Question("Translate 'thanks'", "გმადლობ")

        s.awaitCorrection(q, mockBufferedReader)

        verify(mockBufferedReader, times(1)).readLine()
    }

    @Test(timeout = 50)
    fun canAwaitCorrectionAfterMultipleAttempts() {
        val input = BufferedReader(StringReader("junk1\njunk2\nგმადლობ"))
        val q = Question("Translate 'thanks'", "გმადლობ")

        s.awaitCorrection(q, input)
    }
}