/*Created on 03/05/18. */
interface Overlay<in Q : Question, out M : Mark> {
    fun printWith(printer: ColourPrinter)
    fun clear()
    fun maxLineLength(): Int
    fun runQuestion(s: Screen, q: Q): M
}

object BaseOverlay : Overlay<Question, Mark> {
    override fun runQuestion(s: Screen, q: Question): Mark {
        TODO("not implemented for BaseOverlay") //To change body of created functions use File | Settings | File Templates.
    }

    override fun toString(): String {
        return ""
    }

    override fun printWith(printer: ColourPrinter) {
        printer.printlnWhite("BASE-OVERLAY")
    }

    override fun maxLineLength(): Int {
        return 12
    }

    override fun clear() {}
}
