/*Created on 03/05/18. */
open class TranslationOverlay : Overlay {
    var questionLine = Text("")
    var answerLine = Text("")
    var correctionLines = Pair(Text(""), Text(""))

    override fun toString(): String {
        return questionLine.toString() + answerLine.toLine() + correctionLines.first.toLine() + correctionLines.second.toLine()
    }

    override fun printWith(printer: ColourPrinter) {
        questionLine.printlnWith(printer)
        answerLine.printlnWith(printer)
        correctionLines.first.printlnWith(printer)
        correctionLines.second.printlnWith(printer)
    }

    override fun showQuestion(q: TranslationQuestion) {
        questionLine = Text("Translate \"${q.given}\"")
    }

    override fun showAnswer(a: String) {
        answerLine = Text(a)
    }

    override fun showMarkedAnswer(translationMark: TranslationMark) {
        if (translationMark.correct) {
            answerLine.setAllGreen()
        } else {
            answerLine.setAllGreen()
            answerLine.setRed(translationMark.errorIndices)
            showCorrection(translationMark.correctAnswer, translationMark.errorIndices)
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