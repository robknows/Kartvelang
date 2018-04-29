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
        val q = Question("What is 2+2?", "4")
        questions.add(q)
        assertEquals(1, questions.count())
    }

    @Test
    fun canAddMultipleQuestionsInOrder() {
        val questions = Questions()
        val q1 = Question("What is 2+1?", "3")
        questions.add(q1)
        val q2 = Question("What is 2+2?", "4")
        questions.add(q2)
        val q3 = Question("What is 2+3?", "5")
        questions.add(q3)
        assertEquals(q1, questions.set[0])
        assertEquals(q2, questions.set[1])
        assertEquals(q3, questions.set[2])
        assertEquals(3, questions.set.count())
    }

    @Test
    fun canPopNextQuestion() {
        val questions = Questions()
        val q1 = Question("What is 2+1?", "3")
        questions.add(q1)
        val q2 = Question("What is 2+2?", "4")
        questions.add(q2)
        val q3 = Question("What is 2+3?", "5")
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
        assertEquals(NullQuestion, questions.pop())
    }

    @Test
    fun canCheckIfEmpty() {
        val questions = Questions()
        assertTrue(questions.empty())
    }
}