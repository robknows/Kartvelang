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
    fun canMarkAnIncorrectAnswerThatIsTooLong() {
        val q = TranslateQuestion("abc", "def")

        val mark = q.markAnswer("memes")

        TestCase.assertEquals(setOf(0, 1, 2, 3, 4), mark.errorIndices)
    }

    @Test
    fun canMarkAnIncorrectAnswerThatIsTooShort() {
        val q = TranslateQuestion("abc", "def")

        val mark = q.markAnswer("a")

        TestCase.assertEquals(setOf(0, 1, 2), mark.errorIndices)
    }

    @Test
    fun ignoresExcessWhitespaceAfterAnswer() {
        val q = TranslateQuestion("thanks", "გმადლობ")

        val mark = q.markAnswer("გმადლობ   ")

        assertTrue(mark.correct)
        assertEquals(0, mark.errorIndices.count())
    }

    @Test
    fun ignoresExcessWhitespaceBeforeAnswer() {
        val q = TranslateQuestion("thanks", "გმადლობ")

        val mark = q.markAnswer("    გმადლობ")

        assertTrue(mark.correct)
        assertEquals(0, mark.errorIndices.count())
    }

    @Test
    fun ignoresExcessWhitespaceOnBothSidesOfAnswer() {
        val q = TranslateQuestion("thanks", "გმადლობ")

        val mark = q.markAnswer("    გმადლობ    ")

        assertTrue(mark.correct)
        assertEquals(0, mark.errorIndices.count())
    }

    @Test
    fun excessWhitespaceAfterAnswerDoesntCauseMarkingProblemsForIncorrectAnswers() {
        val q = TranslateQuestion("thanks", "გმადლობ")

        val mark = q.markAnswer("დმადლომ   ")

        assertFalse(mark.correct)
        assertEquals(setOf(0, 6), mark.errorIndices)
    }

    @Test
    fun excessWhitespaceBeforeAnswerDoesntCauseMarkingProblemsForIncorrectAnswers() {
        val q = TranslateQuestion("thanks", "გმადლობ")

        val mark = q.markAnswer("    დმადლომ")

        assertFalse(mark.correct)
        assertEquals(setOf(0, 6), mark.errorIndices)

    }

    @Test
    fun excessWhitespaceBothSidesDoesntCauseMarkingProblemsForIncorrectAnswers() {
        val q = TranslateQuestion("thanks", "გმადლობ")

        val mark = q.markAnswer("    დმადლომ    ")

        assertFalse(mark.correct)
        assertEquals(setOf(0, 6), mark.errorIndices)
    }

    @Test
    fun ignoresQuestionMarksWhenMarking() {
        val q = TranslateQuestion("how are you", "როგორ ხარ")

        val mark = q.markAnswer("როგორ ხარ?")

        assertTrue(mark.correct)
        assertEquals(0, mark.errorIndices.count())
    }

    @Test
    fun ignoresCommasWhenMarking() {
        val q = TranslateQuestion("how are you", "როგორ ხარ")

        val mark = q.markAnswer("როგორ, ხარ")

        assertTrue(mark.correct)
        assertEquals(0, mark.errorIndices.count())

    }

    @Test
    fun ignoresFullStopsWhenMarking() {
        val q = TranslateQuestion("how are you", "როგორ ხარ")

        val mark = q.markAnswer("როგორ ხარ.")

        assertTrue(mark.correct)
        assertEquals(0, mark.errorIndices.count())
    }

    @Test
    fun ignoresGrammarEvenIfItsInTheAnswerText() {
        val q = TranslateQuestion("hello, I am called Keti", "გამარჯობა, მე მქვია ქეთი")

        val mark = q.markAnswer("გამარჯობა მე მქვია ქეთი")

        assertTrue(mark.correct)
        assertEquals(0, mark.errorIndices.count())
    }

    @Test
    fun ignoresExcessWhitespaceBetweenWords() {
        val q = TranslateQuestion("hello, I am called Keti", "გამარჯობა, მე მქვია ქეთი")

        val mark = q.markAnswer("გამარჯობა  მე  მქვია  ქეთი")

        assertTrue(mark.correct)
        assertEquals(0, mark.errorIndices.count())
    }
}