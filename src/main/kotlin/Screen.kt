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

    fun showQuestion(q: Question) {
        lines.add(Pair(Q, Text(q.questionText)))
    }

    fun awaitAnswer(source: BufferedReader): Text {
        val readLine = source.readLine()
        return if (readLine == null) {
            NullText
        } else {
            Text(readLine)
        }
    }

    fun showAnswer(a: String) {
        lines.add(Pair(A, Text(a)))
    }

    fun print() {
        lines.forEach({(_, txt) -> txt.printWith(printer) })
    }

    fun showAnswerGreen() {
        lines.find({ (label, _) -> label == A })?.second?.setGreen()
    }
}

enum class LineLabel {
    Q,
    A
}