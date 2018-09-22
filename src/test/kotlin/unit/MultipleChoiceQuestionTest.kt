/*Created on 04/05/18. */
package unit

import junit.framework.TestCase
import logic.question.MultipleChoiceChoice
import logic.question.MultipleChoiceChoice.A
import logic.question.MultipleChoiceMark
import logic.question.MultipleChoiceQuestion
import org.junit.Assert.assertEquals
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

    @Test
    fun canHandleAnAnswerThatIsntAProperChoice() {
        val q = MultipleChoiceQuestion("makes a sound like \"m\" in \"monkey\"", "მ", Triple("გ", "ლ", "ო"), A)

        val mark = q.markAnswer("z")

        assertEquals(MultipleChoiceMark(false, MultipleChoiceChoice.NONE, A), mark)
    }
}