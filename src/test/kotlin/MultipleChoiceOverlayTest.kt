/*Created on 04/05/18. */
import Colour.*
import Colour.B
import MultipleChoiceChoice.*
import junit.framework.TestCase.assertEquals
import org.junit.Test
import org.mockito.Mockito.inOrder
import org.mockito.Mockito.spy

class MultipleChoiceOverlayTest {
    val o = MultipleChoiceOverlay()

    @Test
    fun canShowQuestion() {
        val q = MultipleChoiceQuestion("makes a sound like \"m\" in \"monkey\"", "მ", Triple("გ", "ლ", "ო"), A)

        o.showQuestion(q)

        assertEquals("Which of these makes a sound like \"m\" in \"monkey\"?\n  მ    გ\n  ლ    ო", o.toString())
    }

    @Test
    fun showingCorrectMarkedAnswerGoesGreen() {
        val m = MultipleChoiceMark(true, A, A)

        o.showMarkedAnswer(m)

        assertEquals(G, o.choice1.baseColour)
        assertEquals(W, o.choice2.baseColour)
        assertEquals(W, o.choice3.baseColour)
        assertEquals(W, o.choice4.baseColour)
    }

    @Test
    fun showingIncorrectMarkedAnswerGoesRedAndCorrectOneGoesBlue() {
        val m = MultipleChoiceMark(false, C, A)

        o.showMarkedAnswer(m)

        assertEquals(B, o.choice1.baseColour)
        assertEquals(W, o.choice2.baseColour)
        assertEquals(R, o.choice3.baseColour)
        assertEquals(W, o.choice4.baseColour)
    }

    @Test
    fun canGetMaxLengthLine() {
        val q = MultipleChoiceQuestion("makes a sound like \"m\" in \"monkey\"", "მ", Triple("გ", "ლ", "ო"), A)

        o.showQuestion(q)

        assertEquals(50, o.maxLineLength())
    }

    @Test
    fun canPrintOverlay() {
        val q = MultipleChoiceQuestion("makes a sound like \"m\" in \"monkey\"", "მ", Triple("გ", "ლ", "ო"), A)
        val spyPrinter = spy(ColourPrinter())
        val inOrder = inOrder(spyPrinter)

        o.showQuestion(q)
        o.printWith(spyPrinter)

        inOrder.verify(spyPrinter).printlnWhite("Which of these makes a sound like \"m\" in \"monkey\"?")
        inOrder.verify(spyPrinter).printWhite("  ")
        inOrder.verify(spyPrinter).print(W, "მ")
        inOrder.verify(spyPrinter).printWhite("    ")
        inOrder.verify(spyPrinter).println(W, "გ")
        inOrder.verify(spyPrinter).printWhite("  ")
        inOrder.verify(spyPrinter).print(W, "ლ")
        inOrder.verify(spyPrinter).printWhite("    ")
        inOrder.verify(spyPrinter).println(W, "ო")
    }

    @Test
    fun canShowQuestionWithAnswerChoiceNotA() {
        val q = MultipleChoiceQuestion("makes a sound like \"m\" in \"monkey\"", "მ", Triple("გ", "ლ", "ო"), D)

        o.showQuestion(q)

        assertEquals("Which of these makes a sound like \"m\" in \"monkey\"?\n  გ    ლ\n  ო    მ", o.toString())
    }

    @Test
    fun showingIncorrectMarkedAnswerGoesRedAndCorrectOneGoesBlueWithAnswerNotA() {
        val m = MultipleChoiceMark(false, C, MultipleChoiceChoice.B)

        o.showMarkedAnswer(m)

        assertEquals(W, o.choice1.baseColour)
        assertEquals(B, o.choice2.baseColour)
        assertEquals(R, o.choice3.baseColour)
        assertEquals(W, o.choice4.baseColour)
    }

    @Test
    fun canPrintCorrectMarkedOverlayWithAnswerNotA() {
        val q = MultipleChoiceQuestion("makes a sound like \"m\" in \"monkey\"", "მ", Triple("გ", "ლ", "ო"), C)
        val spyPrinter = spy(ColourPrinter())
        val inOrder = inOrder(spyPrinter)

        o.showQuestion(q)
        o.showMarkedAnswer(MultipleChoiceMark(true, C, C))
        o.printWith(spyPrinter)

        inOrder.verify(spyPrinter).printlnWhite("Which of these makes a sound like \"m\" in \"monkey\"?")
        inOrder.verify(spyPrinter).printWhite("  ")
        inOrder.verify(spyPrinter).print(W, "გ")
        inOrder.verify(spyPrinter).printWhite("    ")
        inOrder.verify(spyPrinter).println(W, "ლ")
        inOrder.verify(spyPrinter).printWhite("  ")
        inOrder.verify(spyPrinter).print(G, "მ")
        inOrder.verify(spyPrinter).printWhite("    ")
        inOrder.verify(spyPrinter).println(W, "ო")
    }
}