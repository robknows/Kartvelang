/*Created on 29/04/18. */
@file:JvmName("MainClass")

package logic

import logic.MultipleChoiceChoice.B
import logic.MultipleChoiceChoice.C
import org.jnativehook.GlobalScreen
import org.jnativehook.NativeHookException
import java.io.BufferedReader
import java.io.InputStreamReader

fun main(args: Array<String>) {
    val printer = ColourPrinter()

    printTitle(printer)

    val input = BufferedReader(InputStreamReader(System.`in`))
    val s = Screen(printer, KeyWaiter(), input)

    val p = Productions()

    val q1 = MultipleChoiceQuestion("makes a sound like \"m\" in \"monkey\"", "მ", Triple("გ", "ლ", "ო"), B)
    val q2 = p.dictionary(greeting_hello)
    val q3 = MultipleChoiceQuestion("makes a sound like \"d\" in \"daisy\"", "დ", Triple("გ", "ლ", "ო"), C)
    val q4 = p.introduction(greeting_nicetomeetyou, name_Keti)

    val qs = Questions(listOf(q1, q2, q3, q4))

    val lesson = Lesson(s, qs, TranslationOverlay(), MultipleChoiceOverlay())

    val u = User()

    u.complete(lesson)

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