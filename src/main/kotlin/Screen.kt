/*Created on 29/04/18. */
import LineLabel.*
import java.io.BufferedReader

open class Screen(private val printer: ColourPrinter, private val keyWaiter: KeyWaiter, var input: BufferedReader) {
    var lines: MutableList<Pair<LineLabel, Text>> = mutableListOf()

    override fun toString(): String {
        return if (lines.isEmpty()) {
            ""
        } else {
            lines.map({ (_, txt) -> txt.toString() }).reduce({ acc, nxt -> acc + "\n" + nxt })
        }
    }

    open fun clear() { lines.clear() }

    open fun close() { input.close() }

    open fun print() {
        val maxLengthLine = lines.map({ (_, txt) -> txt.toString().length }).max() ?: 0
        printer.printlnWhite("-".repeat(maxLengthLine))
        lines.forEach({(_, txt) -> txt.printlnWith(printer) })
        printer.printlnWhite("-".repeat(maxLengthLine))
    }

    open fun awaitAnswer(): Text {
        val readLine = input.readLine()
        return if (readLine == null || readLine.isEmpty()) {
            awaitAnswer()
        } else {
            Text(readLine)
        }
    }

    private fun prompt(s: String) {
        val prompt = Pair(C, Text(s))
        lines.add(prompt)
        print()
        lines.remove(prompt)
    }

    open fun awaitCorrection(correctAnswer: String) {
        prompt("Type out the correct answer:")
        while (input.readLine()!! != correctAnswer) {}
    }

    open fun awaitKeyPress(key: Key) {
        prompt("Press " + key.name.toLowerCase() + " to continue")
        if (key == Key.ENTER) {
            input.readLine()
        } else {
            keyWaiter.await(key.keyCode)
        }
    }

    open fun showTranslationQuestion(q: TranslationQuestion) {
        lines.add(Pair(Q, Text("Translate \"${q.given}\"")))
    }

    open fun showAnswer(a: String) {
        lines.add(Pair(A, Text(a)))
    }

    open fun showMarkedAnswer(translationMark: TranslationMark) {
        if (translationMark.correct) {
            showAnswerCorrect()
        } else {
            showAnswerIncorrectIndices(translationMark.errorIndices)
            showCorrection(translationMark.correctAnswer, translationMark.errorIndices)
        }
    }

    private fun answerText(): Text? {
        return lines.find({ (label, _) -> label == A })?.second
    }

    fun showAnswerCorrect() {
        answerText()?.setAllGreen()
    }

    fun showAnswerEntirelyIncorrect() {
        answerText()?.setAllRed()
    }

    open fun showAnswerIncorrectIndices(indices: Set<Int>) {
        val answerText = answerText()
        if (answerText != null) {
            answerText.setAllGreen()
            answerText.setRed(indices)
        }
    }

    fun showCorrection(correctAnswer: String, errorIndices: Set<Int>) {
        val corrections = Text(correctAnswer.mapIndexed({ i, c ->
            if (i in errorIndices) {
                c
            } else {
                ' '
            }
        }).joinToString(separator = ""))
        corrections.baseColour = Colour.B

        val fullCorrection = Text("correct answer: $correctAnswer")
        fullCorrection.baseColour = Colour.W
        fullCorrection.overlayColour = Colour.B
        fullCorrection.overlayIndices = (0..15).toMutableSet()

        lines.add(Pair(C, corrections))
        lines.add(Pair(C, fullCorrection))
    }

    fun showPostLessonInfo(accuracyPc: Double, seconds: Double, hint: String) {
        val acc = if (accuracyPc == 100.0) { "100" } else { accuracyPc.toString() }
        lines.add(Pair(I, Text("Accuracy:    $acc%%")))
        lines.add(Pair(I, Text("Lesson time: " + seconds.toString() + " seconds")))
        lines.add(Pair(I, Text("Hint: $hint")))
    }
}

enum class LineLabel {
    Q, // TranslationQuestion
    A, // Answer
    C, // Correction
    I, // Information
}