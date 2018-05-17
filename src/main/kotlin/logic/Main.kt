/*Created on 29/04/18. */
@file:JvmName("MainClass")

package logic

import course.lesson_whatareyoucalled
import logic.io.ColourPrinter
import logic.io.Screen
import logic.overlay.MultipleChoiceOverlay
import logic.overlay.TranslationOverlay
import java.io.BufferedReader
import java.io.InputStreamReader

fun main(args: Array<String>) {
    val printer = ColourPrinter()
    val input = BufferedReader(InputStreamReader(System.`in`))
    val s = Screen(printer, input)

    printTitle(printer)

    val u = User()

    u.complete(lesson_whatareyoucalled, s, TranslationOverlay(), MultipleChoiceOverlay())

    u.saveProfile("/home/rob/kartvelang_user.json")

    System.exit(0)
}

fun printTitle(printer: ColourPrinter) {
    printer.printBlue("=== ")
    printer.printRed("kar")
    printer.printGreen("tve")
    printer.printWhite("lang")
    printer.printlnBlue(" ===")
}