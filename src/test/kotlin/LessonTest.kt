/*Created on 30/04/18. */
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Test
import org.mockito.Matchers
import org.mockito.Mockito.*
import java.io.BufferedReader
import java.io.StringReader

class LessonTest {
    @Test(timeout = 1000)
    fun canCompleteSimpleLesson() {
        val mockPrinter = mock(ColourPrinter::class.java)
        val mockKeyWaiter = mock(KeyWaiter::class.java)
        val input = BufferedReader(StringReader("abc\n\ndoremi\n\nonetwothree\n\n"))
        val s = Screen(mockPrinter, mockKeyWaiter, input)

        val qs = Questions()
        qs.add(Question("Type \"abc\"", "abc"))
        qs.add(Question("Type \"doremi\"", "doremi"))
        qs.add(Question("Type \"onetwothree\"", "onetwothree"))

        val lesson = Lesson(s, qs)

        lesson.complete()

        verify(mockPrinter, times(23)).printlnWhite(Matchers.anyString())
        verify(mockPrinter, times(3)).printlnGreen(Matchers.anyString())
        verify(mockPrinter, never()).printlnRed(Matchers.anyString())
    }

    @Test(timeout = 1000)
    fun canGetLessonResults() {
        val mockPrinter = mock(ColourPrinter::class.java)
        val mockKeyWaiter = mock(KeyWaiter::class.java)
        val input = BufferedReader(StringReader("abc\n\ndoremu\ndoremi\n\nonetwothree\n\ndoremi\n\n"))
        val s = Screen(mockPrinter, mockKeyWaiter, input)

        val qs = Questions()
        qs.add(Question("Type \"abc\"", "abc"))
        qs.add(Question("Type \"doremi\"", "doremi"))
        qs.add(Question("Type \"onetwothree\"", "onetwothree"))

        val lesson = Lesson(s, qs)

        val lessonResults = lesson.complete()

        assertEquals(75.0, lessonResults.accuracyPc)
        assertTrue(lessonResults.timeSeconds < 1.0)
        assertTrue(lessonResults.timeSeconds > 0.0)
    }

    @Test(timeout = 1000)
    fun canCompleteLoadedGreetingsLesson() {
        val mockPrinter = mock(ColourPrinter::class.java)
        val mockKeyWaiter = mock(KeyWaiter::class.java)
        val input = BufferedReader(StringReader("გმადლობ\n\nგამარჯობა\n\nსასიამოვნუა\n\nროგორ ხარ?\n\nკარგად\n\n"))
        val s = Screen(mockPrinter, mockKeyWaiter, input)

        val filename = "../../src/test/resources/questions_loader_test_1.json"
        val qs = Questions(filename)

        val lesson = Lesson(s, qs)

        val lessonResults = lesson.complete()

        assertEquals(100.0, lessonResults.accuracyPc)
        assertTrue(lessonResults.timeSeconds < 1.0)
        assertTrue(lessonResults.timeSeconds > 0.0)
    }
}