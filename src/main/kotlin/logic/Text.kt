/*Created on 29/04/18. */
package logic

import logic.Colour.*

open class Text(private val text: String) {
    var baseColour = W
    var overlayColour = R
    var overlayIndices = mutableSetOf<Int>()

    override fun toString(): String {
        return text
    }

    fun toLine(): String {
        return if (text.isEmpty()) {
            ""
        } else {
            "\n" + text
        }
    }

    fun printlnWith(printer: ColourPrinter) {
        if (!text.isEmpty()) {
            if (overlayIndices.isEmpty()) {
                printer.println(baseColour, text)
            } else {
                for (i in 0..(text.length - 1)) {
                    if (i in overlayIndices) {
                        printer.print(overlayColour, text[i].toString())
                    } else {
                        printer.print(baseColour, text[i].toString())
                    }
                }
                println()
            }
        }
    }

    fun setAllGreen() {
        baseColour = G
    }

    fun setAllRed() {
        baseColour = R
    }

    fun setAllBlue() {
        baseColour = B
    }

    fun setRed(indices: Set<Int>) {
        overlayIndices.addAll(indices.toMutableList())
    }
}