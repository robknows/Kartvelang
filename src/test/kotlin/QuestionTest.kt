/*Created on 29/04/18. */
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
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
    fun canDiffAnIncorrectWord() {
        val q = Question("Translate 'thanks'", "გმადლობ")
        val a = "გმადლომ"
        val mark = q.markAnswer(a)
        assertEquals(listOf(6), mark.errorIndices)
    }
}