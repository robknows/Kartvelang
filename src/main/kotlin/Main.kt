/*Created on 29/04/18. */
@file:JvmName("MainClass")

import MultipleChoiceChoice.A
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

    val p = Productions()

    val q1 = MultipleChoiceQuestion("makes a sound like \"m\" in \"monkey\"", "მ", Triple("გ", "ლ", "ო"), A)
    val q2 = p.dictionary(greeting_hello)
    val q3 = MultipleChoiceQuestion("makes a sound like \"d\" in \"daisy\"", "დ", Triple("გ", "ლ", "ო"), A)
    val q4 = p.introduction(greeting_nicetomeetyou, name_Keti)

    val qs = Questions(listOf(q1, q2, q3, q4))

    val lesson = Lesson(s, qs, TranslationOverlay(), MultipleChoiceOverlay())

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