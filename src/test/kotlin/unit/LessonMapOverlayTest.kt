/*Created on 22/09/18. */
package unit

import course.lesson_hello
import course.lesson_whatareyoucalled
import logic.User
import logic.io.Colour
import logic.io.ColourPrinter
import logic.io.Screen
import logic.lesson.Lesson
import logic.lesson.Questions
import logic.lesson.QuickLesson
import logic.overlay.LessonMapOverlay
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.spy
import java.io.BufferedReader
import java.io.StringReader

class LessonMapOverlayTest {
    private val mockScreen = mock(Screen::class.java)
    private val lessons: Set<Lesson> = setOf(lesson_hello, lesson_whatareyoucalled)

    @Test
    fun canPrintLessonMapWithPrompt() {
        val testPrinter = spy(ColourPrinter())

        val testLesson1 = QuickLesson("L1", Questions())
        val testLesson2 = QuickLesson("L2", Questions())
        val testLesson3 = QuickLesson("L3", Questions())

        val lessons = setOf(testLesson1, testLesson2, testLesson3)

        val o = LessonMapOverlay(lessons, mockScreen)
        o.showMap()
        o.showLessonSelectionPrompt()

        val inOrder = Mockito.inOrder(testPrinter)

        o.printWith(testPrinter)

        inOrder.verify(testPrinter).println(Colour.W, "Choose a lesson")
        inOrder.verify(testPrinter).println(Colour.W, "- L1")
        inOrder.verify(testPrinter).println(Colour.W, "- L2")
        inOrder.verify(testPrinter).println(Colour.W, "- L3")
    }

    @Test
    fun canGetMaxLineLength() {
        val testLesson1 = QuickLesson("L1", Questions())
        val testLesson2 = QuickLesson("L2", Questions())
        val testLesson3 = QuickLesson("L3", Questions())

        val lessons = setOf(testLesson1, testLesson2, testLesson3)

        val o = LessonMapOverlay(lessons, mockScreen)

        assertEquals(15, o.maxLineLength())
    }

    @Test
    fun canGetMaxLineLengthWithLongLessonName() {
        val testLesson1 = QuickLesson("really long lesson name", Questions())
        val testLesson2 = QuickLesson("L2", Questions())
        val testLesson3 = QuickLesson("L3", Questions())

        val lessons = setOf(testLesson1, testLesson2, testLesson3)

        val o = LessonMapOverlay(lessons, mockScreen)

        assertEquals(25, o.maxLineLength())
    }

    @Test
    fun canNavigateLessonMap() {
        val mockPrinter = mock(ColourPrinter::class.java)
        val testInput = BufferedReader(StringReader("hello"))
        val testScreen = Screen(mockPrinter, testInput)
        val mockUser = mock(User::class.java)
        val lessonMapOverlay = LessonMapOverlay(lessons, testScreen)

        Mockito.`when`(mockUser.availableLessons(lessons)).thenReturn(lessons)


        assertEquals(lesson_hello, lessonMapOverlay.awaitLessonSelection())
    }

    @Test
    fun canNavigateLessonMapWithTypo() {
        val mockPrinter = mock(ColourPrinter::class.java)
        val testInput = BufferedReader(StringReader("hellp\nhello"))
        val testScreen = Screen(mockPrinter, testInput)
        val mockUser = mock(User::class.java)
        val lessonMapOverlay = LessonMapOverlay(lessons, testScreen)

        Mockito.`when`(mockUser.availableLessons(lessons)).thenReturn(lessons)


        assertEquals(lesson_hello, lessonMapOverlay.awaitLessonSelection())
    }

    @Test
    fun canNavigateLessonMapWithoutWorryingAboutPunctuationOrCapitals() {
        val mockPrinter = mock(ColourPrinter::class.java)
        val testInput = BufferedReader(StringReader("What are YOU    called"))
        val testScreen = Screen(mockPrinter, testInput)
        val mockUser = mock(User::class.java)
        val lessonMapOverlay = LessonMapOverlay(lessons, testScreen)

        Mockito.`when`(mockUser.availableLessons(lessons)).thenReturn(lessons)

        assertEquals(lesson_whatareyoucalled, lessonMapOverlay.awaitLessonSelection())
    }
}