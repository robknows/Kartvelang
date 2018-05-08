/*Created on 29/04/18. */
import LineLabel.C
import LineLabel.I
import java.io.BufferedReader
import kotlin.math.max

open class Screen(private val printer: ColourPrinter, private val keyWaiter: KeyWaiter, var input: BufferedReader) {
    var lines: MutableList<Pair<LineLabel, Text>> = mutableListOf()
    var questionOverlay: QuestionOverlay<*, *> = BaseOverlay

    override fun toString(): String {
        val linesString = if (lines.isEmpty()) {
            ""
        } else {
            lines.map({ (_, txt) -> txt.toString() }).reduce({ acc, nxt -> acc + "\n" + nxt })
        }
        if (questionOverlay.toString().isEmpty()) {
            return linesString
        } else {
            return "$questionOverlay\n$linesString"
        }
    }

    open fun clear() {
        questionOverlay.clear()
        lines.clear()
    }

    open fun close() { input.close() }

    open fun print() {
        val maxLengthLine = max(maxLineLength(), questionOverlay.maxLineLength())
        printer.printlnWhite("-".repeat(maxLengthLine))
        questionOverlay.printWith(printer)
        printLines()
        printer.printlnWhite("-".repeat(maxLengthLine))
    }

    private fun maxLineLength(): Int {
        return lines.map({ (_, txt) -> txt.toString().length }).max() ?: 0
    }

    private fun printLines() {
        if (lines.count() != 0) {
            lines.forEach({ (_, txt) -> txt.printlnWith(printer) })
        }
    }

    open fun awaitLine(): Text {
        val readLine = input.readLine()
        return if (readLine == null || readLine.isEmpty()) {
            awaitLine()
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

    open fun awaitKeyPress(key: Key) {
        prompt("Press " + key.name.toLowerCase() + " to continue")
        if (key == Key.ENTER) {
            input.readLine()
        } else {
            keyWaiter.await(key.keyCode)
        }
    }

    open fun awaitCorrection(q: Question) {
        if (q.fullCorrections) {
            prompt("Type out the correct answer:")
            while (!q.verifyAnswer(input.readLine()!!)) {
            }
        }
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