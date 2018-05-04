/*Created on 29/04/18. */
@file:JvmName("MainClass")

import org.jnativehook.GlobalScreen
import org.jnativehook.NativeHookException
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

fun main(args: Array<String>) {
    val printer = ColourPrinter()

    printTitle(printer)

    val input = BufferedReader(InputStreamReader(System.`in`))
    val s = Screen(printer, KeyWaiter(), input)

    val small = Questions(Productions().introductionQuestions(listOf(greeting_hello), listOf(farewell_seeyousoon), listOf(name_Keti), listOf()))

    val lesson = Lesson(s, small, TranslationOverlay())

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
    val hints = listOf(
            "All nominative nouns in Georgian end in a vowel",
            "Georgian has no long vowels",
            "Nouns whose root ends in a consonant have \"ი\" added to the end in the nominative case",
            "Georgian uses postpositions rather than prepositions")
    return hints.randomChoice()
}

private fun <E> List<E>.randomChoice(): E {
    return get(Random().nextInt(size))
}

fun registerKeyboardHook() {
    try {
        GlobalScreen.registerNativeHook()
    } catch (ex: NativeHookException) {
        println("Couldn't create keyboard hook")
        System.exit(1)
    }
}