/*Created on 03/05/18. */
interface Overlay<Q : Question, M : Mark> {
    fun printWith(printer: ColourPrinter)
    fun showQuestion(q: Q)
    fun showAnswer(a: String)
    fun showMarkedAnswer(m: M)
    fun clear()
    fun maxLineLength(): Int
    fun runQuestion(s: Screen, q: Q): M
}

object BaseOverlay : Overlay<Question, Mark> {
    override fun showQuestion(q: Question) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showAnswer(a: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showMarkedAnswer(m: Mark) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun runQuestion(s: Screen, q: Question): Mark {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun printWith(printer: ColourPrinter) {
        printer.printlnWhite("BASE-OVERLAY")
    }

    override fun maxLineLength(): Int {
        return 12
    }

    override fun clear() {}
}
