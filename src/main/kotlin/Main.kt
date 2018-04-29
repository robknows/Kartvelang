/*Created on 29/04/18. */
@file:JvmName("MainClass")

fun main(args: Array<String>) {
    printRed("red  ")
    printGreen("green  ")
    printlnWhite("white")
}

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