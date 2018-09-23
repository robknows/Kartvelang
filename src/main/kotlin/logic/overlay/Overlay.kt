/*Created on 03/05/18. */
package logic.overlay

import logic.io.ColourPrinter
import logic.io.Screen
import logic.question.Mark
import logic.question.Question

interface Overlay {
    /*
    All the IO for the overlays should happen in this method, using the provided ColourPrinter.
    Other areas of the overlay should manipulate only the Screen or the internal objects used
    to provide the desired representation at print-time.
    */
    fun printWith(printer: ColourPrinter)

    fun clear()
    fun maxLineLength(): Int
}

interface QuestionOverlay<in Q : Question, out M : Mark> : Overlay {
    fun runQuestion(s: Screen, q: Q): M
}

object BaseOverlay : Overlay {
    override fun printWith(printer: ColourPrinter) {
        printer.printlnWhite("     ")
    }

    override fun toString(): String {
        return ""
    }

    override fun maxLineLength(): Int {
        return 5
    }

    override fun clear() {}
}
