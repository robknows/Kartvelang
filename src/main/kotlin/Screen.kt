/*Created on 29/04/18. */
import LineLabel.*
import org.jnativehook.GlobalScreen
import org.jnativehook.keyboard.NativeKeyEvent
import org.jnativehook.keyboard.NativeKeyListener
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

    fun awaitAnswer(source: BufferedReader): Text {
        val readLine = source.readLine()
        return if (readLine == null) {
            NullText
        } else {
            Text(readLine)
        }
    }

    fun awaitCorrection(q: Question, source: BufferedReader) {
        while (source.readLine()!! != q.answerText) {}
    }

    fun awaitContinue() {
        printer.printlnWhite("Press enter to continue")
        KeyWaiter(NativeKeyEvent.VC_ENTER).await()
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

    fun showCorrection(q: Question, errorIndices: Set<Int>) {
        val indicesCorrection = Text(q.answerText.mapIndexed({ i, c ->
            if (i in errorIndices) {
                c
            } else {
                ' '
            }
        }).joinToString(separator = ""))
        indicesCorrection.baseColour = Colour.B

        val fullCorrection = Text("correct answer: " + q.answerText)
        fullCorrection.baseColour = Colour.W
        fullCorrection.overlayColour = Colour.B
        fullCorrection.overlayIndices = (0..15).toMutableSet()

        lines.add(Pair(C, indicesCorrection))
        lines.add(Pair(C, fullCorrection))
    }
}

enum class LineLabel {
    Q, // Question
    A, // Answer
    C  // Correction
}

class KeyWaiter(private val keyCode: Int) {
    private class KeyListener(private val keyCode: Int, @Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN") private val lock: Object) : NativeKeyListener {
        override fun nativeKeyPressed(e: NativeKeyEvent?) {
            if (e != null) {
                if (e.keyCode == keyCode) {
                    synchronized(lock) {
                        lock.notifyAll()
                    }
                }
            }
        }

        override fun nativeKeyReleased(e: NativeKeyEvent?) {}

        override fun nativeKeyTyped(e: NativeKeyEvent?) {}
    }

    fun await() {
        try {
            val lock = java.lang.Object()
            val keyListener = KeyListener(keyCode, lock)
            GlobalScreen.addNativeKeyListener(keyListener)
            synchronized(lock) {
                lock.wait()
            }
            GlobalScreen.removeNativeKeyListener(keyListener)
        } catch (e: Exception) {
            e.printStackTrace()
            println("Exiting")
            System.exit(1)
        }
    }
}