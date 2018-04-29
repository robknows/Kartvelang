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
}

enum class Colour {
    WHITE, // 255
    GREEN, // 028
    RED    // 196
}