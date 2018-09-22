package unit

/*Created on 29/04/18. */
import junit.framework.TestCase.assertEquals
import logic.io.Colour
import logic.io.Text
import org.junit.Test

class TextTest {
    @Test
    fun canSetTextGreen() {
        val t = Text("hello")

        t.setAllGreen()

        assertEquals(Colour.G, t.baseColour)
    }

    @Test
    fun canSetTextRed() {
        val t = Text("hello")

        t.setAllRed()

        assertEquals(Colour.R, t.baseColour)
    }

    @Test
    fun canSetOverlayIndices() {
        val t = Text("hello")

        t.setAllGreen()
        t.setRed(setOf(2))

        assertEquals(Colour.G, t.baseColour)
        assertEquals(Colour.R, t.overlayColour)
        assertEquals(mutableSetOf(2), t.overlayIndices)
    }
}