/*Created on 29/04/18. */
import java.io.BufferedReader

class Screen(val printer: ColourPrinter) {
    var lines: MutableList<Text> = mutableListOf()

    override fun toString(): String {
        return if (lines.isEmpty()) {
            ""
        } else {
            lines.map({ txt -> txt.toString() }).reduce({ acc, nxt -> acc + "\n" + nxt })
        }
    }

    fun showQuestion(q: Question) {
        lines.add(Text(q.questionText))
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
        lines.add(Text(a))
    }

    fun print() {
        lines.forEach({txt -> txt.printWith(printer) })
    }
}