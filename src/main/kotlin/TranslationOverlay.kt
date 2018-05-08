/*Created on 03/05/18. */
open class TranslationOverlay : QuestionOverlay<TranslationQuestion, TranslationMark> {
    var questionLine = Text("")
    var answerLine = Text("")
    var correctionLines = Pair(Text(""), Text(""))

    override fun runQuestion(s: Screen, q: TranslationQuestion): TranslationMark {
        s.questionOverlay = this
        showQuestion(q)
        s.print()
        val a = s.awaitLine().toString()
        showAnswer(a)
        val mark = q.markAnswer(a)
        showMarkedAnswer(mark)
        return mark
    }

    override fun toString(): String {
        return questionLine.toString() + answerLine.toLine() + correctionLines.first.toLine() + correctionLines.second.toLine()
    }

    override fun printWith(printer: ColourPrinter) {
        questionLine.printlnWith(printer)
        answerLine.printlnWith(printer)
        correctionLines.first.printlnWith(printer)
        correctionLines.second.printlnWith(printer)
    }

    fun showQuestion(q: TranslationQuestion) {
        questionLine = Text("Translate \"${q.given}\"")
    }

    fun showAnswer(a: String) {
        answerLine = Text(a)
    }

    fun showMarkedAnswer(m: TranslationMark) {
        if (m.correct) {
            answerLine.setAllGreen()
        } else {
            answerLine.setAllGreen()
            answerLine.setRed(m.errorIndices)
            showCorrection(m.correctAnswer, m.errorIndices)
        }
    }

    private fun showCorrection(correctAnswer: String, errorIndices: Set<Int>) {
        val corrections = Text(correctAnswer.mapIndexed({ i, c ->
            if (i in errorIndices) {
                c
            } else {
                ' '
            }
        }).joinToString(separator = ""))
        corrections.baseColour = Colour.B

        val fullCorrection = Text("Correct answer: $correctAnswer")
        fullCorrection.baseColour = Colour.W
        fullCorrection.overlayColour = Colour.B
        fullCorrection.overlayIndices = (0..15).toMutableSet()

        correctionLines = Pair(corrections, fullCorrection)
    }

    override fun clear() {
        questionLine = Text("")
        answerLine = Text("")
        correctionLines = Pair(Text(""), Text(""))
    }

    override fun maxLineLength(): Int {
        return listOf(questionLine, answerLine, correctionLines.first, correctionLines.second)
                .map({ line -> line.toString().length })
                .max() ?: 0
    }
}