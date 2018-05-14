/*Created on 29/04/18. */
package logic

open class ColourPrinter {
    fun printRed(s: String) {
        val proc = ProcessBuilder("/bin/bash", "-c", "printf '\\e[38;5;196m$s'").start()
        val readLine = proc.inputStream.bufferedReader().readLine()
        print(readLine)
    }

    fun printGreen(s: String) {
        val proc = ProcessBuilder("/bin/bash", "-c", "printf '\\e[38;5;028m$s'").start()
        val readLine = proc.inputStream.bufferedReader().readLine()
        print(readLine)
    }

    open fun printWhite(s: String) {
        val proc = ProcessBuilder("/bin/bash", "-c", "printf '\\e[38;5;255m$s'").start()
        val readLine = proc.inputStream.bufferedReader().readLine()
        print(readLine)
    }

    fun printBlue(s: String) {
        val proc = ProcessBuilder("/bin/bash", "-c", "printf '\\e[38;5;021m$s'").start()
        val readLine = proc.inputStream.bufferedReader().readLine()
        print(readLine)
    }

    open fun printlnRed(s: String) {
        printRed(s)
        println()
    }

    open fun printlnGreen(s: String) {
        printGreen(s)
        println()
    }

    open fun printlnWhite(s: String) {
        printWhite(s)
        println()
    }

    open fun printlnBlue(s: String) {
        printBlue(s)
        println()
    }

    open fun print(c: Colour, s: String) {
        when (c) {
            Colour.W -> printWhite(s)
            Colour.G -> printGreen(s)
            Colour.R -> printRed(s)
            Colour.B -> printBlue(s)
        }
    }

    open fun println(c: Colour, s: String) {
        when (c) {
            Colour.W -> printlnWhite(s)
            Colour.G -> printlnGreen(s)
            Colour.R -> printlnRed(s)
            Colour.B -> printlnBlue(s)
        }
    }
}

enum class Colour {
    W, // 255
    G, // 028
    R, // 196
    B, // 021
}