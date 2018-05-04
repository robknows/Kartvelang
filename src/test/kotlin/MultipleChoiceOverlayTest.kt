/*Created on 04/05/18. */
import junit.framework.TestCase.assertEquals
import org.junit.Test

class MultipleChoiceOverlayTest {
    val o = MultipleChoiceOverlay()

    @Test
    fun canShowQuestion() {
        val q = MultipleChoiceQuestion("makes a sound like \"m\" in \"monkey\"", "მ", Triple("გ", "ლ", "ო"))

        o.showQuestion(q)

        assertEquals("Which of these makes a sound like \"m\" in \"monkey\"?\n  მ    გ\n  ლ    ო", o.toString())
    }

    @Test
    fun showingJustTheAnswerDoesntDoAnything() {
        val q = MultipleChoiceQuestion("makes a sound like \"m\" in \"monkey\"", "მ", Triple("გ", "ლ", "ო"))

        o.showQuestion(q)
        o.showAnswer("გ")

        assertEquals("Which of these makes a sound like \"m\" in \"monkey\"?\n  მ    გ\n  ლ    ო", o.toString())
    }

    @Test
    fun showingCorrectMarkedAnswerGoesGreen() {
        val m = MultipleChoiceMark(true, MultipleChoiceChoice.A)

        o.showMarkedAnswer(m)

        assertEquals(Colour.G, o.choice1.baseColour)
        assertEquals(Colour.W, o.choice2.baseColour)
        assertEquals(Colour.W, o.choice3.baseColour)
        assertEquals(Colour.W, o.choice4.baseColour)
    }
}