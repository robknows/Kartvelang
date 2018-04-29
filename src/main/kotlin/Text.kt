/*Created on 29/04/18. */
open class Text(private val text: String) {
    var colour = Colour.WHITE

    override fun toString(): String {
        return text
    }

    fun printWith(printer: ColourPrinter) {
        when(colour) {
            Colour.WHITE -> printer.printlnWhite(text)
            Colour.GREEN -> printer.printlnGreen(text)
            Colour.RED   -> printer.printlnRed(text)
        }
    }

    fun setGreen() {
        colour = Colour.GREEN
    }

    fun setRed() {
        colour = Colour.RED
    }
}

object NullText : Text("")