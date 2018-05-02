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
    val q1 = TranslateQuestion("Type \"abc\"", "abc")
    val q2 = TranslateQuestion("Type \"doremi\"", "doremi")
    val q3 = TranslateQuestion("Type \"onetwothree\"", "onetwothree")
    val exampleQs = Questions(listOf(q1, q2, q3))

    @Test(timeout = 1000)
    fun canCompleteSimpleLessonWithAllCorrectAnswers() {
        val input = BufferedReader(StringReader("abc\n\ndoremi\n\nonetwothree\n\n"))
        val mockPrinter = mock(ColourPrinter::class.java)
        val mockKeyWaiter = mock(KeyWaiter::class.java)
        val spyScreen = spy(Screen(mockPrinter, mockKeyWaiter, input))
        val inOrder = Mockito.inOrder(spyScreen)

        val lesson = Lesson(spyScreen, exampleQs)

        lesson.complete()

        inOrder.verify(spyScreen).showTranslateQuestion(q1)
        inOrder.verify(spyScreen).showAnswer("abc")
        inOrder.verify(spyScreen).showMarkedAnswer(q1.markAnswer("abc"))

        inOrder.verify(spyScreen).showTranslateQuestion(q2)
        inOrder.verify(spyScreen).showAnswer("doremi")
        inOrder.verify(spyScreen).showMarkedAnswer(q2.markAnswer("doremi"))

        inOrder.verify(spyScreen).showTranslateQuestion(q3)
        inOrder.verify(spyScreen).showAnswer("onetwothree")
        inOrder.verify(spyScreen).showMarkedAnswer(q3.markAnswer("onetwothree"))

        verify(spyScreen, times(3)).awaitKeyPress(Key.ENTER)
        verify(spyScreen, times(6)).print()
        verify(spyScreen, times(3)).clear()
        verify(spyScreen).close()

        verify(spyScreen, never()).awaitCorrection(Matchers.anyString())
        verify(spyScreen, never()).showAnswerIncorrectIndices(Matchers.anySetOf(Int::class.java))
    }

    @Test(timeout = 2700)
    fun canGetLessonResults() {
        val mockPrinter = mock(ColourPrinter::class.java)
        val mockKeyWaiter = mock(KeyWaiter::class.java)
        val input = BufferedReader(StringReader("abc\n\ndoremu\ndoremi\n\nonetwothree\n\ndoremi\n\n"))
        val s = Screen(mockPrinter, mockKeyWaiter, input)

        val qs = Questions()
        qs.add(TranslateQuestion("Type \"abc\"", "abc"))
        qs.add(TranslateQuestion("Type \"doremi\"", "doremi"))
        qs.add(TranslateQuestion("Type \"onetwothree\"", "onetwothree"))

        val lesson = Lesson(s, qs)

        val lessonResults = lesson.complete()

        assertEquals(75.0, lessonResults.accuracyPc)
        assertTrue(lessonResults.timeSeconds < 1.0)
        assertTrue(lessonResults.timeSeconds > 0.0)
    }
}