/*Created on 29/04/18. */
@file:JvmName("MainClass")

fun main(args: Array<String>) {
    val colourPrinter = ColourPrinter()
    colourPrinter.printRed("red  ")
    colourPrinter.printGreen("green  ")
    colourPrinter.printlnWhite("white")

    val s = Screen(colourPrinter)
    val q = Question("Translate \"thanks\"", "გმადლობ")
    s.showQuestion(q)
    val a = "გმადლომ"
    s.showAnswer(a)

    s.showAnswerGreen()

    s.print()
}