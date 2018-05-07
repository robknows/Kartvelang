/*Created on 29/04/18. */
import MultipleChoiceChoice.A
import junit.framework.TestCase.assertEquals
import org.junit.Test
import org.mockito.Matchers
import org.mockito.Mockito.*
import java.io.BufferedReader
import java.io.StringReader

class ScreenTest {
    private val mockKeyWaiter = mock(KeyWaiter::class.java)
    private val mockBufferedReader = mock(BufferedReader::class.java)
    private val spyPrinter = spy(ColourPrinter())
    private val s = Screen(spyPrinter, mockKeyWaiter, mockBufferedReader)

    val realisticExampleOverlay = object : Overlay<TranslationQuestion, TranslationMark> {
        var line1 = Text("A question!")
        var line2 = Text("A correct answer!")
        var line3 = Text("Some blue stuff")

        override fun showQuestion(q: TranslationQuestion) {
            TODO("not needed")
        }

        override fun showAnswer(a: String) {
            TODO("not needed")
        }

        override fun showMarkedAnswer(m: TranslationMark) {
            TODO("not needed")
        }

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
            TODO("not needed")
        }
    }

    val tackyExampleOverlay = object : Overlay<TranslationQuestion, TranslationMark> {
        override fun showQuestion(q: TranslationQuestion) {
            TODO("not needed")
        }

        override fun showAnswer(a: String) {
            TODO("not needed")
        }

        override fun showMarkedAnswer(m: TranslationMark) {
            TODO("not needed")
        }

        override fun clear() {
            TODO("not needed")
        }

        override fun maxLineLength(): Int {
            return 17
        }

        override fun printWith(printer: ColourPrinter) {
            printer.printlnWhite("A question!")
            printer.printlnGreen("A correct answer!")
            printer.printlnBlue("Some blue stuff")
        }

        override fun runQuestion(s: Screen, q: TranslationQuestion): TranslationMark {
            TODO("not needed")
        }
    }

    @Test
    fun initialisedBlank() {
        assertEquals("", s.toString())
    }

    @Test(timeout = 700)
    fun canAwaitAnswer() {
        doReturn("4").`when`(mockBufferedReader).readLine()

        assertEquals("4", s.awaitAnswer().toString())
    }

    @Test(timeout = 700)
    fun canAwaitAnswerIgnoringWhitespace() {
        s.input = BufferedReader(StringReader("\n\n\n4"))

        assertEquals("4", s.awaitAnswer().toString())
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

    @Test(timeout = 260)
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
}