import junit.framework.TestCase.assertFalse
import org.junit.Test

/*Created on 29/04/18. */
class QuestionTest {
    @Test
    fun canRecogniseIncorrectAnswer() {
        val q = Question("Translate 'thanks'", "გმადლობ")
        val a = "გმადლომ"
        val mark = q.markAnswer(a)
        assertFalse(mark.correct)
    }
}