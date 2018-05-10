/*Created on 29/04/18. */
import LineLabel.C
import LineLabel.I
import java.io.BufferedReader
import kotlin.math.max

open class Screen(private val printer: ColourPrinter, private val keyWaiter: KeyWaiter, var input: BufferedReader) {
    var lines: MutableList<Pair<LineLabel, Text>> = mutableListOf()
    var overlay: Overlay = BaseOverlay

    override fun toString(): String {
        val linesString = if (lines.isEmpty()) {
            ""
        } else {
            lines.map({ (_, txt) -> txt.toString() }).reduce({ acc, nxt -> acc + "\n" + nxt })
        }
        if (overlay.toString().isEmpty()) {
            return linesString
        } else {
            return "$overlay\n$linesString"
        }
    }

    open fun clear() {
        overlay.clear()
        lines.clear()
    }

    open fun closeInput() {
        input.close()
    }

    open fun print() {
        val maxLengthLine = max(maxLineLength(), overlay.maxLineLength())
        printer.printlnWhite("-".repeat(maxLengthLine))
        overlay.printWith(printer)
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

    open fun awaitLine(): String {
        val readLine = input.readLine()
        return if (readLine == null || readLine.isEmpty()) {
            awaitLine()
        } else {
            readLine
        }
    }

    private fun prompt(s: String) {
        val prompt = Pair(C, Text(s))
        lines.add(prompt)
        print()
        lines.remove(prompt)
    }

    fun awaitPromptedLine(prompt: String): String {
        prompt(prompt)
        return awaitLine()
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

    open fun showPostLessonInfo(accuracyPc: Double, seconds: Double, hint: String) {
        val acc = if (accuracyPc == 100.0) { "100" } else { accuracyPc.toString() }
        lines.add(Pair(I, Text("Accuracy:    $acc%%")))
        lines.add(Pair(I, Text("Lesson time: " + seconds.toString() + " seconds")))
        lines.add(Pair(I, Text("Hint: $hint")))
    }
}

enum class LineLabel {
    C, // Correction
    I, // Information
}