/*Created on 29/04/18. */
import LineLabel.*
import java.io.BufferedReader

class Screen(private val printer: ColourPrinter, private val keyWaiter: KeyWaiter, var input: BufferedReader) {
    var lines: MutableList<Pair<LineLabel, Text>> = mutableListOf()

    override fun toString(): String {
        return if (lines.isEmpty()) {
            ""
        } else {
            lines.map({ (_, txt) -> txt.toString() }).reduce({ acc, nxt -> acc + "\n" + nxt })
        }
    }

    fun clear() { lines.clear() }

    fun close() { input.close() }

    fun print() {
        val maxLengthLine = lines.map({ (_, txt) -> txt.toString().length }).max() ?: 0
        printer.printlnWhite("-".repeat(maxLengthLine))
        lines.forEach({(_, txt) -> txt.printlnWith(printer) })
        printer.printlnWhite("-".repeat(maxLengthLine))
    }

    fun awaitAnswer(): Text {
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

    fun awaitCorrection(correctAnswer: String) {
        prompt("Type out the correct answer:")
        while (input.readLine()!! != correctAnswer) {}
    }

    fun awaitKeyPress(key: Key) {
        prompt("Press " + key.name.toLowerCase() + " to continue")
        if (key == Key.ENTER) {
            input.readLine()
        } else {
            keyWaiter.await(key.keyCode)
        }
    }

    fun showTranslateQuestion(q: TranslateQuestion) {
        lines.add(Pair(Q, Text("Translate \"${q.given}\"")))
    }

    private fun answerText(): Text? {
        return lines.find({ (label, _) -> label == A })?.second
    }

    fun showAnswer(a: String) {
        lines.add(Pair(A, Text(a)))
    }

    fun showAnswerCorrect() {
        answerText()?.setAllGreen()
    }

    fun showAnswerEntirelyIncorrect() {
        answerText()?.setAllRed()
    }

    fun showAnswerIncorrectIndices(indices: Set<Int>) {
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

    fun showMarkedAnswer(mark: Mark) {
        if (mark.correct) {
            showAnswerCorrect()
        } else {
            showAnswerIncorrectIndices(mark.errorIndices)
            showCorrection(mark.correctAnswer, mark.errorIndices)
        }
    }
}

enum class LineLabel {
    Q, // TranslateQuestion
    A, // Answer
    C, // Correction
    I, // Information
}