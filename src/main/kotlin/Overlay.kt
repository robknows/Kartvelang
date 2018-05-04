/*Created on 03/05/18. */
interface Overlay {
    fun printWith(printer: ColourPrinter)
    fun showQuestion(q: TranslationQuestion)
    fun showAnswer(a: String)
    fun showMarkedAnswer(translationMark: TranslationMark)
    fun clear()
    fun maxLineLength(): Int
}

object BaseOverlay : Overlay {
    override fun printWith(printer: ColourPrinter) {
        printer.printlnWhite("BASE-OVERLAY")
    }

    override fun showQuestion(q: TranslationQuestion) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showMarkedAnswer(translationMark: TranslationMark) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showAnswer(a: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clear() {}

    override fun maxLineLength(): Int {
        return 12
    }
}
