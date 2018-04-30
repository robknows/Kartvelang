/*Created on 29/04/18. */
@file:JvmName("MainClass")

import java.io.BufferedReader
import java.io.InputStreamReader

fun main(args: Array<String>) {
    val colourPrinter = ColourPrinter()
    colourPrinter.printBlue("=== ")
    colourPrinter.printRed("kar")
    colourPrinter.printGreen("tve")
    colourPrinter.printWhite("lang")
    colourPrinter.printlnBlue(" ===")

    val s = Screen(colourPrinter)
    val q = Question("Translate \"thanks\"", "გმადლობ")

    s.showQuestion(q)
    s.print()
    val answerText = s.awaitAnswer(BufferedReader(InputStreamReader(System.`in`))) // "გმადლომ"
    val a = answerText.toString()
    s.showAnswer(a)
    val mark = q.markAnswer(a)
    val errorIndices = mark.errorIndices
    s.showAnswerIncorrectIndices(errorIndices)
    s.showCorrection(q, errorIndices)
    s.print()
    s.awaitCorrection(q, BufferedReader(InputStreamReader(System.`in`)))
}