import Colour.*

/*Created on 29/04/18. */
open class Text(private val text: String) {
    var colour = WHITE

    override fun toString(): String {
        return text
    }

    fun printWith(printer: ColourPrinter) {
        when(colour) {
            WHITE -> printer.printlnWhite(text)
            GREEN -> printer.printlnGreen(text)
            RED   -> printer.printlnRed(text)
        }
    }

    fun setGreen() {
        colour = GREEN
    }

    fun setRed() {
        colour = RED
    }
}

object NullText : Text("")