/*Created on 08/05/18. */
import junit.framework.TestCase.assertEquals
import org.junit.Test

class ModuleMapOverlayTest {
    val o = ModuleMapOverlay()

    @Test
    fun canShowSingleModule() {
        val m = Module("greetings")

        o.showModule(m)

        assertEquals("Available Modules:\n\t-greetings", o.toString())
    }
}