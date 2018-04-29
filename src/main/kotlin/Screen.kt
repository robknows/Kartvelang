/*Created on 29/04/18. */
import LineLabel.*
import java.io.BufferedReader

class Screen(val printer: ColourPrinter) {
    var lines: MutableList<Pair<LineLabel, Text>> = mutableListOf()

    override fun toString(): String {
        return if (lines.isEmpty()) {
            ""
        } else {
            lines.map({ (_, txt) -> txt.toString() }).reduce({ acc, nxt -> acc + "\n" + nxt })
        }
    }

    fun print() {
        lines.forEach({(_, txt) -> txt.printlnWith(printer) })
    }

    fun showQuestion(q: Question) {
        lines.add(Pair(Q, Text(q.questionText)))
    }

    fun showAnswer(a: String) {
        lines.add(Pair(A, Text(a)))
    }

    fun showCorrection(q: Question, errorIndices: Set<Int>) {
        lines.add(Pair(C, Text(q.answerText.mapIndexed({ i, c -> if (i in errorIndices) { c } else { ' ' } }).joinToString(separator = ""))))
        lines.add(Pair(C, Text("correct answer: " + q.answerText)))
    }

    fun awaitAnswer(source: BufferedReader): Text {
        val readLine = source.readLine()
        return if (readLine == null) {
            NullText
        } else {
            Text(readLine)
        }
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

    private fun answerText(): Text? {
        return lines.find({ (label, _) -> label == A })?.second
    }
}

enum class LineLabel {
    Q, // Question
    A, // Answer
    C  // Correction
}