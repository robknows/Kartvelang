/*Created on 30/04/18. */
import MultipleChoiceChoice.A
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Test
import org.mockito.Mockito.*
import java.io.BufferedReader
import java.io.StringReader

class LessonTest {
    val q1 = TranslationQuestion("Type \"abc\"", "abc")
    val q2 = TranslationQuestion("Type \"doremi\"", "doremi")
    val q3 = TranslationQuestion("Type \"onetwothree\"", "onetwothree")
    val q4 = MultipleChoiceQuestion("is \"m\"", "m", Triple("a", "b", "c"), A)
    val spyTranslationOverlay = spy(TranslationOverlay())
    val spyMultipleChoiceOverlay = spy(MultipleChoiceOverlay())

    @Test(timeout = 3000)
    fun canCompleteSimpleLessonWithAllCorrectAnswers() {
        val input = BufferedReader(StringReader("abc\n\ndoremi\n\nonetwothree\n\n"))
        val mockPrinter = mock(ColourPrinter::class.java)
        val mockKeyWaiter = mock(KeyWaiter::class.java)
        val spyScreen = spy(Screen(mockPrinter, mockKeyWaiter, input))
        val inOrder = inOrder(spyTranslationOverlay)
        val lesson = Lesson(spyScreen, Questions(listOf(q1, q2, q3)), spyTranslationOverlay, spyMultipleChoiceOverlay)

        lesson.complete()

        inOrder.verify(spyTranslationOverlay).runQuestion(spyScreen, q1)
        inOrder.verify(spyTranslationOverlay).runQuestion(spyScreen, q2)
        inOrder.verify(spyTranslationOverlay).runQuestion(spyScreen, q3)

        verify(spyScreen, times(3)).awaitKeyPress(Key.ENTER)
        verify(spyScreen, times(6)).print()
        verify(spyScreen, times(3)).clear()
        verify(spyScreen).close()

        verify(spyScreen, never()).awaitCorrection(q1)
        verify(spyScreen, never()).awaitCorrection(q2)
        verify(spyScreen, never()).awaitCorrection(q3)
    }

    @Test(timeout = 3000)
    fun lessonEventsOccurInCorrectOrder() {
        val input = BufferedReader(StringReader("abc\n\n"))
        val mockPrinter = mock(ColourPrinter::class.java)
        val mockKeyWaiter = mock(KeyWaiter::class.java)
        val spyScreen = spy(Screen(mockPrinter, mockKeyWaiter, input))
        val inOrder = inOrder(spyTranslationOverlay, spyScreen)
        val lesson = Lesson(spyScreen, Questions(listOf(q1)), spyTranslationOverlay, spyMultipleChoiceOverlay)

        lesson.complete()

        inOrder.verify(spyTranslationOverlay).runQuestion(spyScreen, q1)
        inOrder.verify(spyScreen).awaitKeyPress(Key.ENTER)
        inOrder.verify(spyScreen).clear()
        inOrder.verify(spyScreen).close()

        verify(spyScreen, times(2)).print()
    }

    @Test(timeout = 3000)
    fun canCompleteSimpleLessonWithAMistake() {
        val input = BufferedReader(StringReader("abc\n\ndoremu\ndoremi\n\nonetwothree\n\ndoremi\n\n"))
        val mockPrinter = mock(ColourPrinter::class.java)
        val mockKeyWaiter = mock(KeyWaiter::class.java)
        val spyScreen = spy(Screen(mockPrinter, mockKeyWaiter, input))
        val inOrder = inOrder(spyTranslationOverlay, spyScreen)
        val lesson = Lesson(spyScreen, Questions(listOf(q1, q2, q3)), spyTranslationOverlay, spyMultipleChoiceOverlay)

        lesson.complete()

        inOrder.verify(spyTranslationOverlay).runQuestion(spyScreen, q1)
        inOrder.verify(spyTranslationOverlay).runQuestion(spyScreen, q2)
        inOrder.verify(spyScreen).awaitCorrection(q2)
        inOrder.verify(spyTranslationOverlay).runQuestion(spyScreen, q3)
        inOrder.verify(spyTranslationOverlay).runQuestion(spyScreen, q2)

        verify(spyScreen, times(4)).awaitKeyPress(Key.ENTER)
        verify(spyScreen, times(9)).print()
        verify(spyScreen, times(4)).clear()
        verify(spyScreen).close()
    }

    @Test(timeout = 3000)
    fun canGetLessonResults() {
        val mockPrinter = mock(ColourPrinter::class.java)
        val mockKeyWaiter = mock(KeyWaiter::class.java)
        val input = BufferedReader(StringReader("abc\n\ndoremu\ndoremi\n\nonetwothree\n\ndoremi\n\n"))

        val lesson = Lesson(Screen(mockPrinter, mockKeyWaiter, input), Questions(listOf(q1, q2, q3)), spyTranslationOverlay, spyMultipleChoiceOverlay)

        val lessonResults = lesson.complete()

        assertEquals(75.0, lessonResults.accuracyPc)
        assertTrue(lessonResults.timeSeconds < 1.0)
        assertTrue(lessonResults.timeSeconds > 0.0)
    }

    @Test(timeout = 3000)
    fun canCompleteLessonWithMultipleChoiceQuestion() {
        val input = BufferedReader(StringReader("abc\n\nA\n\nonetwothree\n\n"))
        val mockPrinter = mock(ColourPrinter::class.java)
        val mockKeyWaiter = mock(KeyWaiter::class.java)
        val spyScreen = spy(Screen(mockPrinter, mockKeyWaiter, input))
        val inOrder = inOrder(spyTranslationOverlay, spyMultipleChoiceOverlay)
        val lesson = Lesson(spyScreen, Questions(listOf(q1, q4, q3)), spyTranslationOverlay, spyMultipleChoiceOverlay)

        lesson.complete()

        inOrder.verify(spyTranslationOverlay).runQuestion(spyScreen, q1)
        inOrder.verify(spyMultipleChoiceOverlay).runQuestion(spyScreen, q4)
        inOrder.verify(spyTranslationOverlay).runQuestion(spyScreen, q3)

        verify(spyScreen, times(3)).awaitKeyPress(Key.ENTER)
        verify(spyScreen, times(6)).print()
        verify(spyScreen, times(3)).clear()
        verify(spyScreen).close()

        verify(spyScreen, never()).awaitCorrection(q1)
        verify(spyScreen, never()).awaitCorrection(q2)
        verify(spyScreen, never()).awaitCorrection(q3)
    }
}