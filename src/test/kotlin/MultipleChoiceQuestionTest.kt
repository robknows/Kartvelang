/*Created on 04/05/18. */
import junit.framework.TestCase
import org.junit.Test

class MultipleChoiceQuestionTest {
    @Test
    fun canRecogniseIncorrectAnswer() {
        val q = MultipleChoiceQuestion("makes a sound like \"m\" in \"monkey\"", "მ", Triple("გ", "ლ", "ო"))

        val mark = q.markAnswer("B")

        TestCase.assertFalse(mark.correct)
    }

    @Test
    fun canRecogniseCorrectAnswer() {
        val q = MultipleChoiceQuestion("makes a sound like \"m\" in \"monkey\"", "მ", Triple("გ", "ლ", "ო"))

        val mark = q.markAnswer("A")

        TestCase.assertTrue(mark.correct)
    }
}