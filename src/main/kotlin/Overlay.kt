/*Created on 03/05/18. */
interface Overlay {
    fun printWith(printer: ColourPrinter)
    fun clear()
    fun maxLineLength(): Int
}

interface QuestionOverlay<in Q : Question, out M : Mark> : Overlay {
    fun runQuestion(s: Screen, q: Q): M
}

object BaseOverlay : Overlay {
    override fun toString(): String {
        return ""
    }

    override fun printWith(printer: ColourPrinter) {
        printer.printlnWhite("     ")
    }

    override fun maxLineLength(): Int {
        return 5
    }

    override fun clear() {}
}
