/*Created on 03/05/18. */
import Colour.G
import Colour.R
import junit.framework.TestCase.assertEquals
import org.junit.Test

class TranslationOverlayTest {
    val o = TranslationOverlay()

    @Test
    fun canShowQuestion() {
        val q = TranslationQuestion("2*2", "4")

        o.showQuestion(q)

        assertEquals("Translate \"2*2\"", o.toString())
    }

    @Test
    fun canAppendAnswerText() {
        val q = TranslationQuestion("thanks", "გმადლობ")

        o.showQuestion(q)
        o.showAnswer("გმადლომ")

        assertEquals("Translate \"thanks\"\nგმადლომ", o.toString())
    }

    @Test
    fun correctAnswerCanBeTurnedGreen() {
        val mark = TranslationMark(true, setOf(), "გმადლობ")

        o.showMarkedAnswer(mark)

        assertEquals(G, o.answerLine.baseColour)
        assertEquals("", o.correctionLines.first.toString())
        assertEquals("", o.correctionLines.second.toString())
    }

    @Test
    fun incorrectAnswerCanBeTurnedRed() {
        val mark = TranslationMark(false, setOf(0, 1, 2, 3, 4, 5, 6), "გმადლობ")

        o.showMarkedAnswer(mark)

        assertEquals(R, o.answerLine.overlayColour)
        assertEquals(mutableSetOf(0, 1, 2, 3, 4, 5, 6), o.answerLine.overlayIndices)
    }

    @Test
    fun incorrectIndicesCanBeTurnedRed() {
        val mark = TranslationMark(false, setOf(6), "გმადლობ")

        o.showMarkedAnswer(mark)

        assertEquals(G, o.answerLine.baseColour)
        assertEquals(R, o.answerLine.overlayColour)
        assertEquals(mutableSetOf(6), o.answerLine.overlayIndices)
    }

    @Test
    fun canAnnotateAnswerWithCorrection() {
        val q = TranslationQuestion("thanks", "გმადლობ")
        val mark = TranslationMark(false, setOf(6), "გმადლობ")

        o.showQuestion(q)
        o.showAnswer("გმადლომ")
        o.showMarkedAnswer(mark)

        assertEquals("Translate \"thanks\"\nგმადლომ\n      ბ\ncorrect answer: გმადლობ", o.toString())
    }
}