/*Created on 30/04/18. */
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Test
import org.mockito.Matchers
import org.mockito.Mockito
import org.mockito.Mockito.*
import java.io.BufferedReader
import java.io.StringReader

class LessonTest {
    val q1 = spy(TranslationQuestion("Type \"abc\"", "abc"))
    val q2 = spy(TranslationQuestion("Type \"doremi\"", "doremi"))
    val q3 = spy(TranslationQuestion("Type \"onetwothree\"", "onetwothree"))

    @Test(timeout = 3000)
    fun canCompleteSimpleLessonWithAllCorrectAnswers() {
        val input = BufferedReader(StringReader("abc\n\ndoremi\n\nonetwothree\n\n"))
        val mockPrinter = mock(ColourPrinter::class.java)
        val mockKeyWaiter = mock(KeyWaiter::class.java)
        val spyScreen = spy(Screen(mockPrinter, mockKeyWaiter, input))
        val inOrder = Mockito.inOrder(spyScreen, q1, q2, q3)

        val lesson = Lesson(spyScreen, Questions(listOf(q1, q2, q3)))

        lesson.complete()

        inOrder.verify(q1).complete(spyScreen, lesson.translationOverlay)
        inOrder.verify(q2).complete(spyScreen, lesson.translationOverlay)
        inOrder.verify(q3).complete(spyScreen, lesson.translationOverlay)

        verify(spyScreen, times(3)).awaitKeyPress(Key.ENTER)
        verify(spyScreen, times(6)).print()
        verify(spyScreen, times(3)).clear()
        verify(spyScreen).close()

        verify(spyScreen, never()).awaitCorrection(Matchers.anyString())
    }

    @Test(timeout = 3000)
    fun lessonEventsOccurInCorrectOrder() {
        val input = BufferedReader(StringReader("abc\n\n"))
        val mockPrinter = mock(ColourPrinter::class.java)
        val mockKeyWaiter = mock(KeyWaiter::class.java)
        val spyScreen = spy(Screen(mockPrinter, mockKeyWaiter, input))

        val inOrder = Mockito.inOrder(spyScreen, q1)

        val lesson = Lesson(spyScreen, Questions(listOf(q1)))

        lesson.complete()

        inOrder.verify(q1).complete(spyScreen, lesson.translationOverlay)
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
        val inOrder = Mockito.inOrder(spyScreen, q1, q2, q3)

        val lesson = Lesson(spyScreen, Questions(listOf(q1, q2, q3)))

        lesson.complete()


        inOrder.verify(q1).complete(spyScreen, lesson.translationOverlay)
        inOrder.verify(q2).complete(spyScreen, lesson.translationOverlay)
        inOrder.verify(spyScreen).awaitCorrection("doremi")
        inOrder.verify(q3).complete(spyScreen, lesson.translationOverlay)
        inOrder.verify(q2).complete(spyScreen, lesson.translationOverlay)

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

        val lesson = Lesson(Screen(mockPrinter, mockKeyWaiter, input), Questions(listOf(q1, q2, q3)))

        val lessonResults = lesson.complete()

        assertEquals(75.0, lessonResults.accuracyPc)
        assertTrue(lessonResults.timeSeconds < 1.0)
        assertTrue(lessonResults.timeSeconds > 0.0)
    }
}