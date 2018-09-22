package unit

import junit.framework.TestCase
import logic.lesson.Questions
import logic.lesson.QuickLesson
import logic.question.MultipleChoiceChoice
import logic.question.MultipleChoiceQuestion
import logic.question.TranslationQuestion
import org.junit.Test

/*Created on 22/09/18. */
class QuickLessonTest {
    val q1 = TranslationQuestion("Type \"abc\"", "abc")
    val q3 = TranslationQuestion("Type \"onetwothree\"", "onetwothree")
    val q4 = MultipleChoiceQuestion("is \"m\"", "m", Triple("a", "b", "c"), MultipleChoiceChoice.B)

    @Test
    fun canCountQuestions() {
        TestCase.assertEquals(3, QuickLesson("", Questions(listOf(q1, q4, q3))).countQuestions())
    }
}