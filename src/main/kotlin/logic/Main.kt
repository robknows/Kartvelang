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
    val input = BufferedReader(InputStreamReader(System.`in`))
    val s = Screen(printer, KeyWaiter(), input)

    printTitle(printer)

    val u = User()
    val p = Productions()

    val lesson = Lesson(
            Questions(
                    listOf(
                            MultipleChoiceQuestion("makes a sound like \"m\" in \"monkey\"", "მ", Triple("გ", "ლ", "ო"), B),
                            p.englishToGeorgian(greeting_hello),
                            MultipleChoiceQuestion("makes a sound like \"d\" in \"daisy\"", "დ", Triple("გ", "ლ", "ო"), C),
                            p.introduction(greeting_nicetomeetyou, name_Keti))))
    val memoLesson = MemoLesson(p, listOf(alphabet_g, alphabet_a, alphabet_m, alphabet_r, alphabet_j, alphabet_b), listOf(greeting_hello))

    u.complete(memoLesson, s, TranslationOverlay(), MultipleChoiceOverlay())

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