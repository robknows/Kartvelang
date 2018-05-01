/*Created on 29/04/18. */
import junit.framework.TestCase
import junit.framework.TestCase.*
import org.junit.Test

class TranslateQuestionTest {
    @Test
    fun canRecogniseIncorrectAnswer() {
        val q = TranslateQuestion("thanks", "გმადლობ")

        val mark = q.markAnswer("გმადლომ")

        assertFalse(mark.correct)
    }

    @Test
    fun canRecogniseCorrectAnswer() {
        val q = TranslateQuestion("thanks", "გმადლობ")

        val mark = q.markAnswer("გმადლობ")

        assertTrue(mark.correct)
    }

    @Test
    fun canDiffAnIncorrectWord() {
        val q = TranslateQuestion("thanks", "გმადლობ")

        val mark = q.markAnswer("გმადლომ")

        assertEquals(setOf(6), mark.errorIndices)
    }

    @Test
    fun canDiffAnCorrectWord() {
        val q = TranslateQuestion("thanks", "გმადლობ")

        val mark = q.markAnswer("გმადლობ")

        assertEquals(0, mark.errorIndices.count())
    }

    @Test
    fun canFlipQuestionAndAnswer() {
        val q = TranslateQuestion("abc", "def").flipped()

        TestCase.assertEquals("def", q.given)
        TestCase.assertEquals("abc", q.answer)
    }
}