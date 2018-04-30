/*Created on 29/04/18. */
@file:JvmName("MainClass")

import org.jnativehook.GlobalScreen
import org.jnativehook.NativeHookException
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

fun main(args: Array<String>) {
    val colourPrinter = ColourPrinter()

    printTitle(colourPrinter)

    try {
        GlobalScreen.registerNativeHook()
    } catch (ex: NativeHookException) {
        println("Couldn't create keyboard hook")
        System.exit(1)
    }
    val s = Screen(colourPrinter, KeyWaiter())

    val qs = Questions()
    qs.add(Question("Type \"abc\"", "abc"))
    qs.add(Question("Type \"doremi\"", "doremi"))
    qs.add(Question("Type \"onetwothree\"", "onetwothree"))

    lesson(s, qs)

    System.exit(0)
}

fun printTitle(colourPrinter: ColourPrinter) {
    colourPrinter.printBlue("=== ")
    colourPrinter.printRed("kar")
    colourPrinter.printGreen("tve")
    colourPrinter.printWhite("lang")
    colourPrinter.printlnBlue(" ===")
}

fun lesson(s: Screen, qs: Questions) {
    val inputStreamReader = InputStreamReader(System.`in`)
    val startTime = Calendar.getInstance().time.time
    while (!qs.empty()) {
        val q = qs.pop()
        s.showQuestion(q)
        s.print()
        val bufferedInput = BufferedReader(inputStreamReader)
        val a = s.awaitAnswer(bufferedInput).toString()
        s.showAnswer(a)
        val mark = q.markAnswer(a)
        if (mark.correct) {
            s.showAnswerCorrect()
            s.print()
        } else {
            val errorIndices = mark.errorIndices
            s.showAnswerIncorrectIndices(errorIndices)
            s.showCorrection(q, errorIndices)
            s.print()
            s.awaitCorrection(q, bufferedInput)
            qs.insertDelayed(q)
        }
        s.awaitKeyPress(Key.ENTER)
        s.clear()
    }
    val endTime = Calendar.getInstance().time.time
    println("Lesson time: " + ((endTime - startTime).toDouble() / 1000).toString() + " seconds")
    inputStreamReader.close()
}