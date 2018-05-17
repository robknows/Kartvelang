/*Created on 29/04/18. */
@file:JvmName("MainClass")

package logic

import course.lesson_hello
import org.jnativehook.GlobalScreen
import org.jnativehook.NativeHookException
import java.io.BufferedReader
import java.io.InputStreamReader

fun main(args: Array<String>) {
    val printer = ColourPrinter()
    val input = BufferedReader(InputStreamReader(System.`in`))
    val s = Screen(printer, KeyWaiter(), input)

    printTitle(printer)

    val u = User()

    u.complete(lesson_hello, s, TranslationOverlay(), MultipleChoiceOverlay())

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

fun registerKeyboardHook() {
    try {
        GlobalScreen.registerNativeHook()
    } catch (ex: NativeHookException) {
        println("Couldn't create keyboard hook")
        System.exit(1)
    }
}