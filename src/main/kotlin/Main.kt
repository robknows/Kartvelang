/*Created on 29/04/18. */
@file:JvmName("MainClass")

import org.jnativehook.GlobalScreen
import org.jnativehook.NativeHookException
import java.io.BufferedReader
import java.io.InputStreamReader

fun main(args: Array<String>) {
    val colourPrinter = ColourPrinter()
    colourPrinter.printBlue("=== ")
    colourPrinter.printRed("kar")
    colourPrinter.printGreen("tve")
    colourPrinter.printWhite("lang")
    colourPrinter.printlnBlue(" ===")

    try {
        GlobalScreen.registerNativeHook()
    } catch (ex: NativeHookException) {
        println("Couldn't listen to keys")
        System.exit(1)
    }

    val s = Screen(colourPrinter)
    val q = Question("Translate \"thanks\"", "გმადლობ")

    s.showQuestion(q)
    s.print()
    val answerText = s.awaitAnswer(BufferedReader(InputStreamReader(System.`in`))) // "გმადლომ" or "გმადლობ"
    val a = answerText.toString()
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
        s.awaitCorrection(q, BufferedReader(InputStreamReader(System.`in`)))
    }
    s.awaitContinue()
    System.exit(0)
}