/*Created on 22/09/18. */
package unit

import course.lesson_hello
import course.lesson_whatareyoucalled
import logic.LessonMap
import logic.User
import logic.io.ColourPrinter
import logic.io.Screen
import logic.lesson.Lesson
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import java.io.BufferedReader
import java.io.StringReader

class LessonMapTest {
    private val lessons: Set<Lesson> = setOf()

    @Test
    fun canNavigateLessonMap() {
        val mockPrinter = mock(ColourPrinter::class.java)
        val testInput = BufferedReader(StringReader("hello"))
        val testScreen = Screen(mockPrinter, testInput)
        val mockUser = mock(User::class.java)
        val lessonMap = LessonMap(mockUser, testScreen, lessons)

        `when`(mockUser.availableLessons(lessons)).thenReturn(setOf(lesson_hello, lesson_whatareyoucalled))


        assertEquals(lesson_hello, lessonMap.navigate())
    }

    @Test
    fun canNavigateLessonMapWithTypo() {
        val mockPrinter = mock(ColourPrinter::class.java)
        val testInput = BufferedReader(StringReader("hellp\nhello"))
        val testScreen = Screen(mockPrinter, testInput)
        val mockUser = mock(User::class.java)
        val lessonMap = LessonMap(mockUser, testScreen, lessons)

        `when`(mockUser.availableLessons(lessons)).thenReturn(setOf(lesson_hello, lesson_whatareyoucalled))


        assertEquals(lesson_hello, lessonMap.navigate())
    }

    @Test
    fun canNavigateLessonMapWithoutWorryingAboutPunctuationOrCapitals() {
        val mockPrinter = mock(ColourPrinter::class.java)
        val testInput = BufferedReader(StringReader("What are YOU    called"))
        val testScreen = Screen(mockPrinter, testInput)
        val mockUser = mock(User::class.java)
        val lessonMap = LessonMap(mockUser, testScreen, lessons)

        `when`(mockUser.availableLessons(lessons)).thenReturn(setOf(lesson_hello, lesson_whatareyoucalled))

        assertEquals(lesson_whatareyoucalled, lessonMap.navigate())
    }
}