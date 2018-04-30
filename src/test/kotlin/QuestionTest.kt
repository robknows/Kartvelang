/*Created on 29/04/18. */
import junit.framework.TestCase.*
import org.junit.Test

class QuestionTest {
    @Test
    fun canRecogniseIncorrectAnswer() {
        val q = Question("Translate 'thanks'", "გმადლობ")
        val a = "გმადლომ"
        val mark = q.markAnswer(a)
        assertFalse(mark.correct)
    }

    @Test
    fun canRecogniseCorrectAnswer() {
        val q = Question("Translate 'thanks'", "გმადლობ")
        val a = "გმადლობ"
        val mark = q.markAnswer(a)
        assertTrue(mark.correct)
    }

    @Test
    fun canDiffAnIncorrectWord() {
        val q = Question("Translate 'thanks'", "გმადლობ")
        val a = "გმადლომ"
        val mark = q.markAnswer(a)
        assertEquals(setOf(6), mark.errorIndices)
    }

    @Test
    fun canDiffAnCorrectWord() {
        val q = Question("Translate 'thanks'", "გმადლობ")
        val a = "გმადლობ"
        val mark = q.markAnswer(a)
        assertEquals(0, mark.errorIndices.count())
    }
}