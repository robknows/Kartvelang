import junit.framework.TestCase.assertEquals
import org.junit.Test

/*Created on 29/04/18. */

class ScreenTest {
    @Test
    fun initialisedBlank() {
        val s = Screen()
        assertEquals("", s.toString())
    }

    @Test
    fun canShowQuestion() {
        val s = Screen()
        val q = Question("What is 2*2?", "4")
        s.showQuestion(q)
        assertEquals("What is 2*2?", s.toString())
    }
}