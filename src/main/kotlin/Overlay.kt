interface Overlay {
    fun printWith(printer: ColourPrinter)
    open fun showQuestion(q: TranslationQuestion)
    open fun showAnswer(a: String)
    open fun showMarkedAnswer(translationMark: TranslationMark)
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
}