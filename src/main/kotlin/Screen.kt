/*Created on 29/04/18. */
import LineLabel.C
import LineLabel.I
import java.io.BufferedReader

open class Screen(private val printer: ColourPrinter, private val keyWaiter: KeyWaiter, var input: BufferedReader) {
    var lines: MutableList<Pair<LineLabel, Text>> = mutableListOf()
    var overlay: Overlay = BaseOverlay

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
        overlay.printWith(printer)
        lines.forEach({(_, txt) -> txt.printlnWith(printer) })
        printer.printlnWhite("-".repeat(maxLengthLine))
    }

    private fun prompt(s: String) {
        val prompt = Pair(C, Text(s))
        lines.add(prompt)
        print()
        lines.remove(prompt)
    }

    open fun awaitAnswer(): Text {
        val readLine = input.readLine()
        return if (readLine == null || readLine.isEmpty()) {
            awaitAnswer()
        } else {
            Text(readLine)
        }
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