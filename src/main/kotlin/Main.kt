/*Created on 29/04/18. */
@file:JvmName("MainClass")

import org.jnativehook.GlobalScreen
import org.jnativehook.NativeHookException
import java.io.BufferedReader
import java.io.InputStreamReader

fun main(args: Array<String>) {
    val printer = ColourPrinter()

    printTitle(printer)

    try {
        GlobalScreen.registerNativeHook()
    } catch (ex: NativeHookException) {
        println("Couldn't create keyboard hook")
        System.exit(1)
    }

    val input = BufferedReader(InputStreamReader(System.`in`))
    val s = Screen(printer, KeyWaiter(), input)

    val qs = Questions()
    qs.add(Question("Type \"abc\"", "abc"))
    qs.add(Question("Type \"doremi\"", "doremi"))
    qs.add(Question("Type \"onetwothree\"", "onetwothree"))
    val lesson = Lesson(s, qs)

    val results = lesson.start()
    s.showLessonAccuracy(results.accuracyPc)
    s.showLessonDuration(results.timeSeconds)
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