import junit.framework.TestCase.assertEquals
import org.junit.Test

/*Created on 29/04/18. */

class ScreenTest {
    @Test
    fun initialisedBlank() {
        val s = Screen()
        assertEquals("", s.toString())
    }
}