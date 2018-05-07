/*Created on 04/05/18. */
import MultipleChoiceChoice.A
import junit.framework.TestCase
import org.junit.Test

class MultipleChoiceQuestionTest {
    @Test
    fun canRecogniseIncorrectAnswer() {
        val q = MultipleChoiceQuestion("makes a sound like \"m\" in \"monkey\"", "მ", Triple("გ", "ლ", "ო"), A)

        val mark = q.markAnswer("B")

        TestCase.assertFalse(mark.correct)
    }

    @Test
    fun canRecogniseCorrectAnswer() {
        val q = MultipleChoiceQuestion("makes a sound like \"m\" in \"monkey\"", "მ", Triple("გ", "ლ", "ო"), A)

        val mark = q.markAnswer("A")

        TestCase.assertTrue(mark.correct)
    }

    @Test
    fun lowerCaseAnswerOkay() {
        val q = MultipleChoiceQuestion("makes a sound like \"m\" in \"monkey\"", "მ", Triple("გ", "ლ", "ო"), A)

        val mark = q.markAnswer("a")

        TestCase.assertTrue(mark.correct)
    }
}