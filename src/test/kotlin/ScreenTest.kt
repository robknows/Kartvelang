/*Created on 29/04/18. */
import junit.framework.TestCase.assertEquals
import org.junit.Test
import org.mockito.Mockito.*
import java.io.BufferedReader
import java.io.StringReader

class ScreenTest {
    private val mockKeyWaiter = mock(KeyWaiter::class.java)
    private val mockBufferedReader = mock(BufferedReader::class.java)
    private val mockPrinter = mock(ColourPrinter::class.java)
    private val s = Screen(mockPrinter, mockKeyWaiter, mockBufferedReader)

    @Test
    fun initialisedBlank() {
        assertEquals("", s.toString())
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

    @Test(timeout = 700)
    fun canAwaitCorrection() {
        doReturn("გმადლობ").`when`(mockBufferedReader).readLine()
        val q = TranslationQuestion("thanks", "გმადლობ")

        s.awaitCorrection(q.answer)

        verify(mockBufferedReader).readLine()
        verify(mockPrinter).printlnWhite("Type out the correct answer:")
    }

    @Test(timeout = 50)
    fun canAwaitCorrectionAfterMultipleAttempts() {
        s.input = BufferedReader(StringReader("junk1\njunk2\nგმადლობ"))
        val q = TranslationQuestion("thanks", "გმადლობ")

        s.awaitCorrection(q.answer)
    }

    @Test
    fun canClearScreen() {
        s.lines = mutableListOf(
                Pair(LineLabel.I, Text("something")),
                Pair(LineLabel.I, Text("somethingElse")),
                Pair(LineLabel.I, Text("somethingAgain")))

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