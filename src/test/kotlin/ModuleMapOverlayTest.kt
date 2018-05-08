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

    @Test
    fun canShowMultipleModules() {
        val m1 = Module("greetings")
        val m2 = Module("introductions")
        val m3 = Module("farewells")

        o.showModule(m1)
        o.showModule(m2)
        o.showModule(m3)

        assertEquals("Available Modules:\n\t-greetings\n\t-introductions\n\t-farewells", o.toString())
    }

    @Test
    fun canClear() {
        val m1 = Module("greetings")
        val m2 = Module("introductions")
        val m3 = Module("farewells")

        o.showModule(m1)
        o.showModule(m2)
        o.showModule(m3)
        o.clear()

        assertEquals("Available Modules:\n", o.toString())
    }
}