/*Created on 29/04/18. */
@file:JvmName("MainClass")

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

    val qs = Questions(p.introductionQuestions(
            listOf(greeting_hello, greeting_nicetomeetyou),
            listOf(farewell_seeyousoon),
            listOf(name_Keti),
            listOf(phrase_whatareyoucalled, mood_alright, mood_notbad)))

    val lesson = Lesson(s, qs)

    val results = lesson.complete()

    s.showPostLessonInfo(results.accuracyPc, results.timeSeconds, randomHint())
    s.print()
    s.clear()

    System.exit(0)
}

fun printTitle(printer: ColourPrinter) {
    printer.printBlue("=== ")
    printer.printRed("kar")
    printer.printGreen("tve")
    printer.printWhite("lang")
    printer.printlnBlue(" ===")
}

fun randomHint(): String {
    return "All nominative nouns in Georgian end in a vowel"
}

fun registerKeyboardHook() {
    try {
        GlobalScreen.registerNativeHook()
    } catch (ex: NativeHookException) {
        println("Couldn't create keyboard hook")
        System.exit(1)
    }
}