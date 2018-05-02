/*Created on 02/05/18. */
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Test

class MultipleChoiceQuestionTest {
    @Test
    fun canMarkMultipleChoiceQuestionCorrect() {
        val q = MultipleChoiceQuestion("m in monkey", "მ", Triple("ო", "გ", "წ"))

        val mark = q.markAnswer("მ")

        assertTrue(mark.correct)
    }

    @Test
    fun canMarkMultipleChoiceQuestionIncorrect() {
        val q = MultipleChoiceQuestion("m in monkey", "მ", Triple("ო", "გ", "წ"))

        val mark = q.markAnswer("ო")

        assertFalse(mark.correct)
    }
}
