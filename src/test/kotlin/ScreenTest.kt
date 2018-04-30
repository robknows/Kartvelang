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
}