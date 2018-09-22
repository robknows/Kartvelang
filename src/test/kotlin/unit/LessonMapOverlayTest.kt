/*Created on 22/09/18. */
package unit

import logic.io.ColourPrinter
import logic.lesson.Questions
import logic.lesson.QuickLesson
import logic.overlay.LessonMapOverlay
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito

class LessonMapOverlayTest {
    @Test
    fun canPrintLessons() {
        val mockPrinter = Mockito.mock(ColourPrinter::class.java)

        val testLesson1 = QuickLesson("L1", Questions())
        val testLesson2 = QuickLesson("L2", Questions())
        val testLesson3 = QuickLesson("L3", Questions())

        val lessons = listOf(testLesson1, testLesson2, testLesson3)

        val o = LessonMapOverlay(lessons)

        val inOrder = Mockito.inOrder(mockPrinter)

        o.printWith(mockPrinter)

        inOrder.verify(mockPrinter).printlnWhite("Choose a lesson")
        inOrder.verify(mockPrinter).printlnWhite("- L1")
        inOrder.verify(mockPrinter).printlnWhite("- L2")
        inOrder.verify(mockPrinter).printlnWhite("- L3")
    }

    @Test
    fun canGetMaxLineLength() {
        val testLesson1 = QuickLesson("L1", Questions())
        val testLesson2 = QuickLesson("L2", Questions())
        val testLesson3 = QuickLesson("L3", Questions())

        val lessons = listOf(testLesson1, testLesson2, testLesson3)

        val o = LessonMapOverlay(lessons)

        assertEquals(15, o.maxLineLength())
    }

    @Test
    fun canGetMaxLineLengthWithLongLessonName() {
        val testLesson1 = QuickLesson("really long lesson name", Questions())
        val testLesson2 = QuickLesson("L2", Questions())
        val testLesson3 = QuickLesson("L3", Questions())

        val lessons = listOf(testLesson1, testLesson2, testLesson3)

        val o = LessonMapOverlay(lessons)

        assertEquals(25, o.maxLineLength())
    }
}