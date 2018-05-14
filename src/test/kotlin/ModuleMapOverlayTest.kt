/*Created on 08/05/18. */
import junit.framework.TestCase.assertEquals
import logic.ColourPrinter
import logic.Module
import logic.ModuleMapOverlay
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.inOrder

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

    @Test
    fun canGetMaxLineLengthForShorterCourseNames() {
        val m1 = Module("greetings")
        val m2 = Module("introductions")
        val m3 = Module("farewells")

        o.showModule(m1)
        o.showModule(m2)
        o.showModule(m3)

        assertEquals(18, o.maxLineLength())
    }

    @Test
    fun canGetMaxLineLengthForLongerCourseNames() {
        val m1 = Module("greetings")
        val m2 = Module("introductions_and_memes_too")
        val m3 = Module("farewells")

        o.showModule(m1)
        o.showModule(m2)
        o.showModule(m3)

        assertEquals(32, o.maxLineLength())
    }

    @Test
    fun canPrintLines() {
        val spyPrinter = Mockito.spy(ColourPrinter())
        val inOrder = inOrder(spyPrinter)
        val m1 = Module("greetings")
        val m2 = Module("introductions")
        val m3 = Module("farewells")

        o.showModule(m1)
        o.showModule(m2)
        o.showModule(m3)
        o.printWith(spyPrinter)

        inOrder.verify(spyPrinter).printlnWhite("Available Modules:")
        inOrder.verify(spyPrinter).printlnWhite("\t-greetings")
        inOrder.verify(spyPrinter).printlnWhite("\t-introductions")
        inOrder.verify(spyPrinter).printlnWhite("\t-farewells")
    }
}