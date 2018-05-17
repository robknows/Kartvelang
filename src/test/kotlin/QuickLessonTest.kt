/*Created on 30/04/18. */
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import logic.io.ColourPrinter
import logic.io.Key
import logic.io.Screen
import logic.lesson.Questions
import logic.lesson.QuickLesson
import logic.overlay.MultipleChoiceOverlay
import logic.overlay.TranslationOverlay
import logic.question.MultipleChoiceChoice.B
import logic.question.MultipleChoiceQuestion
import logic.question.TranslationQuestion
import org.junit.Test
import org.mockito.Matchers
import org.mockito.Mockito.*
import java.io.BufferedReader
import java.io.StringReader

class QuickLessonTest {
    val q1 = TranslationQuestion("Type \"abc\"", "abc")
    val q2 = TranslationQuestion("Type \"doremi\"", "doremi")
    val q3 = TranslationQuestion("Type \"onetwothree\"", "onetwothree")
    val q4 = MultipleChoiceQuestion("is \"m\"", "m", Triple("a", "b", "c"), B)
    val spyTranslationOverlay = spy(TranslationOverlay())
    val spyMultipleChoiceOverlay = spy(MultipleChoiceOverlay())

    @Test(timeout = 3000)
    fun canCompleteSimpleLessonWithAllCorrectAnswers() {
        val input = BufferedReader(StringReader("abc\n\ndoremi\n\nonetwothree\n\n"))
        val mockPrinter = mock(ColourPrinter::class.java)
        val spyScreen = spy(Screen(mockPrinter, input))
        val inOrder = inOrder(spyTranslationOverlay)

        val lesson = QuickLesson(Questions(listOf(q1, q2, q3)))

        lesson.complete(spyScreen, spyTranslationOverlay, spyMultipleChoiceOverlay)

        inOrder.verify(spyTranslationOverlay).runQuestion(spyScreen, q1)
        inOrder.verify(spyTranslationOverlay).runQuestion(spyScreen, q2)
        inOrder.verify(spyTranslationOverlay).runQuestion(spyScreen, q3)

        verify(spyScreen, times(3)).awaitKeyPress(Key.ENTER)
        verify(spyScreen, times(7)).print()
        verify(spyScreen, times(4)).clear()
        verify(spyScreen).closeInput()
        verify(spyScreen).showPostLessonInfo(eq(100.0), Matchers.anyDouble(), Matchers.anyString())

        verify(spyScreen, never()).awaitCorrection(q1)
        verify(spyScreen, never()).awaitCorrection(q2)
        verify(spyScreen, never()).awaitCorrection(q3)
    }

    @Test(timeout = 3000)
    fun lessonEventsOccurInCorrectOrder() {
        val input = BufferedReader(StringReader("abc\n\n"))
        val mockPrinter = mock(ColourPrinter::class.java)
        val spyScreen = spy(Screen(mockPrinter, input))
        val inOrder = inOrder(spyTranslationOverlay, spyScreen)
        val lesson = QuickLesson(Questions(listOf(q1)))

        lesson.complete(spyScreen, spyTranslationOverlay, spyMultipleChoiceOverlay)

        inOrder.verify(spyTranslationOverlay).runQuestion(spyScreen, q1)
        inOrder.verify(spyScreen).awaitKeyPress(Key.ENTER)
        inOrder.verify(spyScreen).clear()
        inOrder.verify(spyScreen).closeInput()
        inOrder.verify(spyScreen).showPostLessonInfo(eq(100.0), Matchers.anyDouble(), Matchers.anyString())

        verify(spyScreen, times(3)).print()
    }

    @Test(timeout = 3000)
    fun canCompleteSimpleLessonWithAMistake() {
        val input = BufferedReader(StringReader("abc\n\ndoremu\ndoremi\n\nonetwothree\n\ndoremi\n\n"))
        val mockPrinter = mock(ColourPrinter::class.java)
        val spyScreen = spy(Screen(mockPrinter, input))
        val inOrder = inOrder(spyTranslationOverlay, spyScreen)
        val lesson = QuickLesson(Questions(listOf(q1, q2, q3)))

        lesson.complete(spyScreen, spyTranslationOverlay, spyMultipleChoiceOverlay)

        inOrder.verify(spyTranslationOverlay).runQuestion(spyScreen, q1)
        inOrder.verify(spyTranslationOverlay).runQuestion(spyScreen, q2)
        inOrder.verify(spyScreen).awaitCorrection(q2)
        inOrder.verify(spyTranslationOverlay).runQuestion(spyScreen, q3)
        inOrder.verify(spyTranslationOverlay).runQuestion(spyScreen, q2)

        verify(spyScreen, times(4)).awaitKeyPress(Key.ENTER)
        verify(spyScreen, times(10)).print()
        verify(spyScreen, times(5)).clear()
        verify(spyScreen).closeInput()
        verify(spyScreen).showPostLessonInfo(eq(75.0), Matchers.anyDouble(), Matchers.anyString())

        verify(spyScreen, never()).awaitCorrection(q1)
        verify(spyScreen, never()).awaitCorrection(q3)
    }

    @Test(timeout = 3000)
    fun canGetLessonResults() {
        val mockPrinter = mock(ColourPrinter::class.java)
        val input = BufferedReader(StringReader("abc\n\ndoremu\ndoremi\n\nonetwothree\n\ndoremi\n\n"))

        val lesson = QuickLesson(Questions(listOf(q1, q2, q3)))

        val lessonResults = lesson.complete(Screen(mockPrinter, input), spyTranslationOverlay, spyMultipleChoiceOverlay)


        assertEquals(75.0, lessonResults.accuracyPc)
        assertTrue(lessonResults.timeSeconds < 1.0)
        assertTrue(lessonResults.timeSeconds > 0.0)
    }

    @Test(timeout = 3000)
    fun canCompleteLessonWithMultipleChoiceQuestion() {
        val input = BufferedReader(StringReader("abc\n\nb\n\nonetwothree\n\n"))
        val mockPrinter = mock(ColourPrinter::class.java)
        val spyScreen = spy(Screen(mockPrinter, input))
        val inOrder = inOrder(spyTranslationOverlay, spyMultipleChoiceOverlay)
        val lesson = QuickLesson(Questions(listOf(q1, q4, q3)))

        lesson.complete(spyScreen, spyTranslationOverlay, spyMultipleChoiceOverlay)

        inOrder.verify(spyTranslationOverlay).runQuestion(spyScreen, q1)
        inOrder.verify(spyMultipleChoiceOverlay).runQuestion(spyScreen, q4)
        inOrder.verify(spyTranslationOverlay).runQuestion(spyScreen, q3)

        verify(spyScreen, times(3)).awaitKeyPress(Key.ENTER)
        verify(spyScreen, times(7)).print()
        verify(spyScreen, times(4)).clear()
        verify(spyScreen).closeInput()
        verify(spyScreen).showPostLessonInfo(eq(100.0), Matchers.anyDouble(), Matchers.anyString())

        verify(spyScreen, never()).awaitCorrection(q1)
        verify(spyScreen, never()).awaitCorrection(q2)
        verify(spyScreen, never()).awaitCorrection(q3)
    }

    @Test
    fun canCountQuestions() {
        assertEquals(3, QuickLesson(Questions(listOf(q1, q4, q3))).countQuestions())
    }
}