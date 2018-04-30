/*Created on 30/04/18. */
import org.junit.Test
import org.mockito.Matchers
import org.mockito.Mockito.*
import java.io.BufferedReader
import java.io.StringReader

class LessonTest {
    @Test
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

        lesson.start()

        verify(mockPrinter, times(24)).printlnWhite(Matchers.anyString())
        verify(mockPrinter, times(3)).printlnGreen(Matchers.anyString())
    }
}