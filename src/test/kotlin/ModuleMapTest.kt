/*Created on 08/05/18. */
import junit.framework.TestCase.assertEquals
import org.junit.Test

class ModuleMapTest {
    @Test
    fun canGetName() {
        val m = Module("greetings")

        assertEquals(m.name, "greetings")
    }
}