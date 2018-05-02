/*Created on 02/05/18. */
import junit.framework.TestCase
import org.junit.Test

class MultipleChoiceQuestionTest {
    @Test
    fun canMarkMultipleChoiceQuestion() {
        val q = MultipleChoiceQuestion("m in monkey", "მ", Triple("ო", "გ", "წ"))

        val mark = q.markAnswer("მ")

        TestCase.assertTrue(mark.correct)
    }
}
