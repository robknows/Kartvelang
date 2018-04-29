/*Created on 29/04/18. */
class ColourPrinter {
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

    fun printWhite(s: String) {
        val proc = ProcessBuilder("/bin/bash", "-c", "printf '\\e[38;5;255m$s'").start()
        val readLine = proc.inputStream.bufferedReader().readLine()
        print(readLine)
    }

    fun printlnRed(s: String) {
        printRed(s)
        println()
    }

    fun printlnGreen(s: String) {
        printGreen(s)
        println()
    }

    fun printlnWhite(s: String) {
        printWhite(s)
        println()
    }

    fun print(c: Colour, s: String) {
        when(c) {
            Colour.W -> printWhite(s)
            Colour.G -> printGreen(s)
            Colour.R -> printRed(s)
        }
    }

    fun println(c: Colour, s: String) {
        when(c) {
            Colour.W -> printlnWhite(s)
            Colour.G -> printlnGreen(s)
            Colour.R -> printlnRed(s)
        }
    }
}

enum class Colour {
    W, // 255
    G, // 028
    R    // 196
}