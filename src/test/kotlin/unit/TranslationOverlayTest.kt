package unit

/*Created on 03/05/18. */
import junit.framework.TestCase.assertEquals
import logic.io.Colour.G
import logic.io.Colour.R
import logic.io.ColourPrinter
import logic.overlay.TranslationOverlay
import logic.question.TranslationMark
import logic.question.TranslationQuestion
import org.junit.Test
import org.mockito.Matchers.anyString
import org.mockito.Mockito.*

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

        assertEquals("Translate \"thanks\"\nგმადლომ\n      ბ\nCorrect answer: გმადლობ", o.toString())
    }

    @Test
    fun canPrintLines() {
        val spyPrinter = spy(ColourPrinter())
        val q = TranslationQuestion("hello", "გამარჯობა")

        o.showQuestion(q)
        o.printWith(spyPrinter)

        verify(spyPrinter, times(1)).printlnWhite(anyString())
    }
}