/*Created on 22/09/18. */
package unit

import course.lesson_hello
import course.lesson_whatareyoucalled
import logic.Kartvelang
import logic.User
import logic.io.ColourPrinter
import logic.io.Screen
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import java.io.BufferedReader
import java.io.StringReader

class KartvelangTest {
    @Test
    fun canNavigateLessonMap() {
        val mockPrinter = mock(ColourPrinter::class.java)
        val testInput = BufferedReader(StringReader("hello"))
        val testScreen = Screen(mockPrinter, testInput)
        val mockUser = mock(User::class.java)

        `when`(mockUser.currentLessons()).thenReturn(listOf(lesson_hello, lesson_whatareyoucalled))

        val k = Kartvelang(testScreen, mockUser)

        assertEquals(lesson_hello, k.navigateLessonMap())
    }
}