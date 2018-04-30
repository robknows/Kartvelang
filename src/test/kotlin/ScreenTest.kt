/*Created on 29/04/18. */
import junit.framework.TestCase.assertEquals
import org.junit.Test
import org.mockito.Mockito.*
import java.io.BufferedReader
import java.io.StringReader

class ScreenTest {
    private val mockKeyWaiter = mock(KeyWaiter::class.java)!!
    private val mockBufferedReader = mock(BufferedReader::class.java)
    private val mockPrinter = mock(ColourPrinter::class.java)
    private val s = Screen(mockPrinter, mockKeyWaiter, mockBufferedReader)

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
        doReturn("4").`when`(mockBufferedReader).readLine()

        assertEquals("4", s.awaitAnswer().toString())
    }

    @Test(timeout = 700)
    fun canAwaitAnswerIgnoringWhitespace() {
        s.input = BufferedReader(StringReader("\n\n\n4"))

        assertEquals("4", s.awaitAnswer().toString())
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
        s.showCorrection(q.answerText, mutableSetOf(6))

        assertEquals("Translate 'thanks'\nგმადლომ\n      ბ\ncorrect answer: გმადლობ", s.toString())
    }

    @Test(timeout = 700)
    fun canAwaitCorrection() {
        doReturn("გმადლობ").`when`(mockBufferedReader).readLine()
        val q = Question("Translate 'thanks'", "გმადლობ")

        s.awaitCorrection(q.answerText)

        verify(mockBufferedReader).readLine()
        verify(mockPrinter).printlnWhite("Type out the correct answer:")
    }

    @Test(timeout = 50)
    fun canAwaitCorrectionAfterMultipleAttempts() {
        s.input = BufferedReader(StringReader("junk1\njunk2\nგმადლობ"))
        val q = Question("Translate 'thanks'", "გმადლობ")

        s.awaitCorrection(q.answerText)
    }

    @Test
    fun canClearScreen() {
        val q = Question("Translate 'thanks'", "გმადლობ")

        s.showQuestion(q)
        s.showAnswer("გმადლომ")
        s.showAnswerIncorrectIndices(mutableSetOf(6))
        s.showCorrection(q.answerText, mutableSetOf(6))
        s.clear()

        assertEquals("", s.toString())
        assertEquals(mutableListOf<Pair<LineLabel, Text>>(), s.lines)
    }

    @Test
    fun canShowPostLessonInfo() {
        s.showPostLessonInfo(100.0, 5.12, "This is a great hint")

        assertEquals("Accuracy:    100%%\nLesson time: 5.12 seconds\nHint: This is a great hint", s.toString())
        assertEquals(3, s.lines.count())
    }
}