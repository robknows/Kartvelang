/*Created on 29/04/18. */
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Test

class QuestionsTest {
    @Test
    fun initialisedEmpty() {
        val questions = Questions()

        assertEquals(questions.count(), 0)
    }

    @Test
    fun canCount() {
        val questions = Questions()

        val q = TranslateQuestion("What is 2+2?", "4")
        questions.add(q)

        assertEquals(1, questions.count())
    }

    @Test
    fun canAddMultipleQuestionsInOrder() {
        val questions = Questions()
        val q1 = TranslateQuestion("What is 2+1?", "3")
        val q2 = TranslateQuestion("What is 2+2?", "4")
        val q3 = TranslateQuestion("What is 2+3?", "5")

        questions.add(q1)
        questions.add(q2)
        questions.add(q3)

        assertEquals(q1, questions.set[0])
        assertEquals(q2, questions.set[1])
        assertEquals(q3, questions.set[2])
        assertEquals(3, questions.set.count())
    }

    @Test
    fun canPopNextQuestion() {
        val questions = Questions()
        val q1 = TranslateQuestion("What is 2+1?", "3")
        val q2 = TranslateQuestion("What is 2+2?", "4")
        val q3 = TranslateQuestion("What is 2+3?", "5")

        questions.add(q1)
        questions.add(q2)
        questions.add(q3)

        assertEquals(q1, questions.pop())
        assertEquals(2, questions.set.count())
        assertEquals(q2, questions.pop())
        assertEquals(1, questions.set.count())
        assertEquals(q3, questions.pop())
        assertEquals(0, questions.set.count())
    }

    @Test
    fun cantPopIfEmpty() {
        val questions = Questions()

        assertEquals(NullTranslateQuestion, questions.pop())
    }

    @Test
    fun canPopIfOnlyOneQuestion() {
        val questions = Questions()
        val q1 = TranslateQuestion("What is 2+1?", "3")
        questions.add(q1)

        assertEquals(q1, questions.pop())
    }

    @Test
    fun canCheckIfEmpty() {
        val questions = Questions()

        assertTrue(questions.empty())
    }

    @Test
    fun canInsertDelayedRepetitionForSmallNumberOfQuestions() {
        val questions = Questions()
        val q1 = TranslateQuestion("What is 2+1?", "3")
        val q2 = TranslateQuestion("What is 2+2?", "4")
        val q3 = TranslateQuestion("What is 2+3?", "5")
        questions.add(q1)
        questions.add(q2)

        questions.insertDelayed(q3)

        assertEquals(q3, questions.set.last())
        assertTrue(questions.set.containsAll(setOf(q1, q2)))
        assertEquals(3, questions.count())
    }

    @Test
    fun canInsertDelayedRepetitionForHigherNumberOfQuestions() {
        val questions = Questions()
        val q1 = TranslateQuestion("What is 2+1?", "3")
        val q2 = TranslateQuestion("What is 2+2?", "4")
        val q3 = TranslateQuestion("What is 2+3?", "5")
        val q4 = TranslateQuestion("What is 2+4?", "6")
        val q5 = TranslateQuestion("What is 2+5?", "7")
        val q6 = TranslateQuestion("What is 2+6?", "8")
        val q7 = TranslateQuestion("What is 2+7?", "9")
        val q8 = TranslateQuestion("What is 2+8?", "10")
        val q9 = TranslateQuestion("What is 2+9?", "11")
        questions.add(q1)
        questions.add(q2)
        questions.add(q3)
        questions.add(q4)
        questions.add(q5)
        questions.add(q6)
        questions.add(q7)
        questions.add(q8)

        questions.insertDelayed(q9)

        assertEquals(q9, questions.set[3])
        assertTrue(questions.set.containsAll(setOf(q1, q2, q3, q4, q5, q6, q7, q8)))
        assertEquals(9, questions.count())
    }

    @Test
    fun canConstructQuestionsFromCollectionOfTranslateQuestion() {
        val q1 = TranslateQuestion("What is 2+1?", "3")
        val q2 = TranslateQuestion("What is 2+2?", "4")
        val q3 = TranslateQuestion("What is 2+3?", "5")

        val questions = listOf(q1, q2, q3)
        val qs = Questions(questions)

        assertEquals(questions, qs.set)
    }
}