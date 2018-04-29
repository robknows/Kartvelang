/*Created on 29/04/18. */
import junit.framework.TestCase.assertEquals
import org.junit.Test
import org.mockito.Mockito
import java.io.BufferedReader

class ScreenTest {
    @Test
    fun initialisedBlank() {
        val s = Screen()
        assertEquals("", s.toString())
    }

    @Test
    fun canShowQuestion() {
        val s = Screen()
        val q = Question("What is 2*2?", "4")
        s.showQuestion(q)
        assertEquals("What is 2*2?", s.toString())
    }

    @Test
    fun canAwaitAnswer() {
        val mockBufferedReader = Mockito.mock(BufferedReader::class.java)
        Mockito.doReturn("4").`when`(mockBufferedReader).readLine()

        val s = Screen()
        val answerText = s.awaitAnswer(mockBufferedReader)
        assertEquals("4", answerText.toString())
    }

    @Test
    fun canAppendAnswerText() {
        val s = Screen()
        val q = Question("Translate 'thanks'", "გმადლობ")
        s.showQuestion(q)
        val a = "გმადლომ"
        s.showAnswer(a)
        assertEquals("Translate 'thanks'\nგმადლომ", s.toString())
    }
}