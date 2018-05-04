/*Created on 04/05/18. */
import junit.framework.TestCase
import org.junit.Test

class MultipleChoiceQuestionTest {
    @Test
    fun canRecogniseIncorrectAnswer() {
        val q = MultipleChoiceQuestion("makes a sound like \"m\" in \"monkey\"", "მ", Triple("გ", "ლ", "ო"))

        val mark = q.markAnswer("გ")

        TestCase.assertFalse(mark.correct)
    }

    @Test
    fun canRecogniseCorrectAnswer() {
        val q = MultipleChoiceQuestion("makes a sound like \"m\" in \"monkey\"", "მ", Triple("გ", "ლ", "ო"))

        val mark = q.markAnswer("მ")

        TestCase.assertTrue(mark.correct)
    }
}