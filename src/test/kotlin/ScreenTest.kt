/*Created on 29/04/18. */
import junit.framework.TestCase.assertEquals
import org.junit.Test
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.mock
import java.io.BufferedReader

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

    @Test
    fun canAwaitAnswer() {
        val mockBufferedReader = mock(BufferedReader::class.java)
        doReturn("4").`when`(mockBufferedReader).readLine()

        val answerText = s.awaitAnswer(mockBufferedReader)
        assertEquals("4", answerText.toString())
    }

    @Test
    fun canAppendAnswerText() {
        val q = Question("Translate 'thanks'", "გმადლობ")
        s.showQuestion(q)
        val a = "გმადლომ"
        s.showAnswer(a)
        assertEquals("Translate 'thanks'\nგმადლომ", s.toString())
    }

    @Test
    fun correctAnswerCanBeTurnedGreen() {
        val q = Question("Translate 'thanks'", "გმადლობ")
        s.showQuestion(q)
        val a = "გმადლობ"
        s.showAnswer(a)
        s.showAnswerCorrect()
        assertEquals(Colour.W, s.lines[0].second.baseColour)
        assertEquals(Colour.G, s.lines[1].second.baseColour)
    }

    @Test
    fun incorrectAnswerCanBeTurnedRed() {
        val q = Question("Translate 'thanks'", "გმადლობ")
        s.showQuestion(q)
        val a = "ayylmao"
        s.showAnswer(a)
        s.showAnswerEntirelyIncorrect()
        assertEquals(Colour.W, s.lines[0].second.baseColour)
        assertEquals(Colour.R, s.lines[1].second.baseColour)
    }

    @Test
    fun incorrectIndicesCanBeTurnedRed() {
        val q = Question("Translate 'thanks'", "გმადლობ")
        s.showQuestion(q)
        val a = "გმადლომ"
        s.showAnswer(a)
        s.showAnswerIncorrectIndices(mutableSetOf(6))
        assertEquals(Colour.W, s.lines[0].second.baseColour)
        assertEquals(Colour.G, s.lines[1].second.baseColour)
        assertEquals(Colour.R, s.lines[1].second.overlayColour)
        assertEquals(mutableSetOf(6), s.lines[1].second.overlayIndices)
    }
}