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

    private fun maxLengthLine(): Int {
        return lines.map({ (_, txt) -> txt.toString().length }).max() ?: 0
    }

    fun print() {
        val maxLengthLine = maxLengthLine()
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

    fun awaitCorrection(correctAnswer: String) {
        printer.printlnWhite("Type out the correct answer:")
        while (input.readLine()!! != correctAnswer) {}
    }

    fun awaitKeyPress(key: Key) {
        printer.printlnWhite("Press " + key.name.toLowerCase() + " to continue")
        if (key == Key.ENTER) {
            input.readLine()
        } else {
            keyWaiter.await(key.keyCode)
        }
    }

    private fun answerText(): Text? {
        return lines.find({ (label, _) -> label == A })?.second
    }

    fun showQuestion(q: Question) {
        lines.add(Pair(Q, Text(q.questionText)))
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
        val indicesCorrection = Text(correctAnswer.mapIndexed({ i, c ->
            if (i in errorIndices) {
                c
            } else {
                ' '
            }
        }).joinToString(separator = ""))
        indicesCorrection.baseColour = Colour.B

        val fullCorrection = Text("correct answer: $correctAnswer")
        fullCorrection.baseColour = Colour.W
        fullCorrection.overlayColour = Colour.B
        fullCorrection.overlayIndices = (0..15).toMutableSet()

        lines.add(Pair(C, indicesCorrection))
        lines.add(Pair(C, fullCorrection))
    }

    fun showLessonDuration(seconds: Double) {
        lines.add(Pair(I, Text("Lesson time: " + seconds.toString() + " seconds")))
    }

    fun clear() {
        lines.clear()
    }

    fun close() {
        input.close()
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
    Q, // Question
    A, // Answer
    C, // Correction
    I, // Information
}