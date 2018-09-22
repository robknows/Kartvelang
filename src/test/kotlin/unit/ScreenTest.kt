package unit

/*Created on 29/04/18. */
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import logic.io.*
import logic.overlay.QuestionOverlay
import logic.question.MultipleChoiceChoice.A
import logic.question.MultipleChoiceQuestion
import logic.question.Question
import logic.question.TranslationMark
import logic.question.TranslationQuestion
import org.junit.Test
import org.mockito.Matchers
import org.mockito.Mockito.*
import java.io.BufferedReader
import java.io.StringReader

class ScreenTest {
    private val mockBufferedReader = mock(BufferedReader::class.java)
    private val spyPrinter = spy(ColourPrinter())
    private val s = Screen(spyPrinter, mockBufferedReader)

    val realisticExampleOverlay = object : QuestionOverlay<TranslationQuestion, TranslationMark> {
        var line1 = Text("A question!")
        var line2 = Text("A correct answer!")
        var line3 = Text("Some blue stuff")

        override fun clear() {
            line1 = Text("")
            line2 = Text("")
            line3 = Text("")
        }

        override fun maxLineLength(): Int {
            return line2.toString().length
        }

        override fun printWith(printer: ColourPrinter) {
            line1.printlnWith(printer)
            line2.printlnWith(printer)
            line3.printlnWith(printer)
        }

        override fun runQuestion(s: Screen, q: TranslationQuestion): TranslationMark {
            throw RuntimeException("realisticExampleOverlay.runQuestion: Shouldn't get called")
        }
    }

    val tackyExampleOverlay = object : QuestionOverlay<TranslationQuestion, TranslationMark> {
        override fun toString(): String {
            return "Tacky overlay string"
        }

        override fun maxLineLength(): Int {
            return 17
        }

        override fun printWith(printer: ColourPrinter) {
            printer.printlnWhite("A question!")
            printer.printlnGreen("A correct answer!")
            printer.printlnBlue("Some blue stuff")
        }

        override fun clear() {
            throw RuntimeException("tackyExampleOverlay.clear: Shouldn't get called")
        }

        override fun runQuestion(s: Screen, q: TranslationQuestion): TranslationMark {
            throw RuntimeException("tackyExampleOverlay.runQuestion: Shouldn't get called")
        }
    }

    @Test
    fun initialisedBlank() {
        assertEquals("", s.toString())
    }

    @Test(timeout = 700)
    fun canAwaitAnswer() {
        doReturn("4").`when`(mockBufferedReader).readLine()

        assertEquals("4", s.awaitLine())
    }

    @Test(timeout = 700)
    fun canAwaitAnswerIgnoringWhitespace() {
        s.input = BufferedReader(StringReader("\n\n\n4"))

        assertEquals("4", s.awaitLine())
    }

    @Test(timeout = 700)
    fun canAwaitCorrection() {
        doReturn("გმადლობ").`when`(mockBufferedReader).readLine()
        val q = TranslationQuestion("thanks", "გმადლობ")

        s.awaitCorrection(q)

        verify(mockBufferedReader).readLine()
        verify(spyPrinter).printlnWhite("Type out the correct answer:")
    }

    @Test(timeout = 700)
    fun canAwaitCorrectionEvenWhenAnswerDoesntMatchExactly() {
        s.input = spy(BufferedReader(StringReader("junk1\njunk2\nგმადლობ\nbut I kept typing anywayyyy")))

        s.awaitCorrection(TranslationQuestion("thanks", "გმადლობ.")) // Note the full stop

        verify(spyPrinter).printlnWhite("Type out the correct answer:")
        verify(s.input, times(3)).readLine()
    }

    @Test(timeout = 120)
    fun canAwaitCorrectionAfterMultipleAttempts() {
        s.input = BufferedReader(StringReader("junk1\njunk2\nგმადლობ"))
        val q = TranslationQuestion("thanks", "გმადლობ")

        s.awaitCorrection(q)
    }

    @Test(timeout = 900)
    fun awaitsCorrectionOnlyIfRequired() {
        doReturn("jibber jabber").`when`(mockBufferedReader).readLine()
        val spyQ = spy(MultipleChoiceQuestion("makes a sound like \"m\" in \"monkey\"", "მ", Triple("გ", "ლ", "ო"), A))

        s.awaitCorrection(spyQ)

        verify(spyQ, never()).verifyAnswer(Matchers.anyString())
    }

    @Test
    fun canClearScreen() {
        s.lines = mutableListOf(
                Pair(LineLabel.I, Text("something")),
                Pair(LineLabel.I, Text("somethingElse")),
                Pair(LineLabel.I, Text("somethingAgain")))

        s.clear()

        assertEquals("", s.toString())
        assertEquals(mutableListOf<Pair<LineLabel, Text>>(), s.lines)
    }

    @Test
    fun canShowPostLessonInfo() {
        s.showPostLessonInfo(100.0, 5.12, "This is a great hint")

        assertEquals("Accuracy:    100%%\nLesson time: 5.12 seconds\nHint: This is a great hint", s.toString())
        assertEquals(3, s.lines.count())
    }

    @Test
    fun screenIsCorrectSize() {
        val inOrder = inOrder(spyPrinter)
        s.overlay = tackyExampleOverlay

        s.print()

        inOrder.verify(spyPrinter).printlnWhite("-----------------")
        inOrder.verify(spyPrinter).printlnWhite("A question!")
        inOrder.verify(spyPrinter).printlnGreen("A correct answer!")
        inOrder.verify(spyPrinter).printlnBlue("Some blue stuff")
        inOrder.verify(spyPrinter).printlnWhite("-----------------")
    }

    @Test
    fun clearScreenAlsoClearsOverlay() {
        s.lines = mutableListOf(
                Pair(LineLabel.I, Text("something")),
                Pair(LineLabel.I, Text("somethingElse")),
                Pair(LineLabel.I, Text("somethingAgain")))
        s.overlay = realisticExampleOverlay

        s.clear()
        s.print()

        verify(spyPrinter, times(2)).printlnWhite("")
        verify(spyPrinter, never()).printlnGreen(Matchers.anyString())
        verify(spyPrinter, never()).printlnBlue(Matchers.anyString())
    }

    @Test
    fun convertingToStringTakesOverlayIntoAccount() {
        s.overlay = tackyExampleOverlay
        s.showPostLessonInfo(100.0, 5.12, "This is a great hint")

        assertEquals("Tacky overlay string\nAccuracy:    100%%\nLesson time: 5.12 seconds\nHint: This is a great hint", s.toString())
    }

    @Test
    fun canAwaitTranslationCorrectionWithBlankAnswers() {
        val mockQuestion = object : Question {
            override val answer: String = "answer!"
            override val fullCorrections: Boolean = true

            override fun verifyAnswer(attempt: String): Boolean {
                assertTrue(attempt.isNotEmpty() && attempt.isNotBlank())
                return attempt == answer
            }
        }

        s.input = BufferedReader(StringReader("\n  \n\n  junk\njunk2\n\n   \n\nanswer!"))

        s.awaitCorrection(mockQuestion)
    }

    @Test
    fun canPrintCoverScreen() {
        s.printCoverScreen()

        verify(spyPrinter).printBlue("=== ")
        verify(spyPrinter).printRed("kar")
        verify(spyPrinter).printGreen("tve")
        verify(spyPrinter).printWhite("lang")
        verify(spyPrinter).printlnBlue(" ===")
    }

    @Test
    fun canPromptForKeyPress() {
        doReturn("jibber jabber").`when`(mockBufferedReader).readLine()
        s.input = mockBufferedReader
        s.promptForKeyPress("Press enter (and print EXACTLY this)", Key.ENTER)
        verify(spyPrinter).printlnWhite("Press enter (and print EXACTLY this)")
    }
}