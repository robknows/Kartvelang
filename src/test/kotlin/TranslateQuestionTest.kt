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

    @Test
    fun canMarkAnIncorrectAnswerOfDifferentLength() {
        val q = TranslateQuestion("abc", "def")

        val mark1 = q.markAnswer("memes")

        TestCase.assertEquals(setOf(0, 1, 2, 3, 4), mark1.errorIndices)

        val mark2 = q.markAnswer("a")

        TestCase.assertEquals(setOf(0, 1, 2), mark2.errorIndices)
    }

    @Test
    fun ignoresExcessWhitespaceWhenMarkingSingleWord() {
        val q = TranslateQuestion("thanks", "გმადლობ")

        val mark1 = q.markAnswer("გმადლობ   ")

        assertTrue(mark1.correct)
        assertEquals(0, mark1.errorIndices.count())

        val mark2 = q.markAnswer("    გმადლობ")

        assertTrue(mark2.correct)
        assertEquals(0, mark2.errorIndices.count())

        val mark3 = q.markAnswer("    გმადლობ    ")

        assertTrue(mark3.correct)
        assertEquals(0, mark3.errorIndices.count())
    }

    @Test
    fun excessWhitespaceDoesntCauseMarkingProblemsForIncorrectAnswers() {
        val q = TranslateQuestion("thanks", "გმადლობ")

        val mark1 = q.markAnswer("დმადლომ   ")

        assertFalse(mark1.correct)
        assertEquals(setOf(0, 6), mark1.errorIndices)

        val mark2 = q.markAnswer("    დმადლომ")

        assertFalse(mark2.correct)
        assertEquals(setOf(0, 6), mark2.errorIndices)

        val mark3 = q.markAnswer("    დმადლომ    ")

        assertFalse(mark3.correct)
        assertEquals(setOf(0, 6), mark3.errorIndices)
    }

    @Test
    fun ignoresGrammarWhenMarking() {
        val q1 = TranslateQuestion("how are you", "როგორ ხარ")

        val mark1 = q1.markAnswer("როგორ ხარ?")

        assertTrue(mark1.correct)
        assertEquals(0, mark1.errorIndices.count())

        val mark2 = q1.markAnswer("როგორ, ხარ")

        assertTrue(mark2.correct)
        assertEquals(0, mark2.errorIndices.count())

        val mark3 = q1.markAnswer("როგორ ხარ.")

        assertTrue(mark3.correct)
        assertEquals(0, mark3.errorIndices.count())

        val q2 = TranslateQuestion("hello, I am called Keti", "გამარჯობა, მე მქვია ქეთი")

        val mark4 = q2.markAnswer("გამარჯობა მე მქვია ქეთი")

        assertTrue(mark4.correct)
        assertEquals(0, mark4.errorIndices.count())
    }

    @Test
    fun ignoresExcessWhitespaceBetweenWords() {
        val q = TranslateQuestion("hello, I am called Keti", "გამარჯობა, მე მქვია ქეთი")

        val mark = q.markAnswer("გამარჯობა  მე  მქვია  ქეთი")

        assertTrue(mark.correct)
        assertEquals(0, mark.errorIndices.count())
    }
}