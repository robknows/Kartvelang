/*Created on 29/04/18. */
@file:JvmName("MainClass")

import org.jnativehook.GlobalScreen
import org.jnativehook.NativeHookException
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

fun main(args: Array<String>) {
    val colourPrinter = ColourPrinter()

    printTitle(colourPrinter)

    try {
        GlobalScreen.registerNativeHook()
    } catch (ex: NativeHookException) {
        println("Couldn't create keyboard hook")
        System.exit(1)
    }

    val input = BufferedReader(InputStreamReader(System.`in`))
    val s = Screen(colourPrinter, KeyWaiter(), input)

    val qs = Questions()
    qs.add(Question("Type \"abc\"", "abc"))
    qs.add(Question("Type \"doremi\"", "doremi"))
    qs.add(Question("Type \"onetwothree\"", "onetwothree"))

    val lesson = Lesson(s, qs)

    lesson.start()

    System.exit(0)
}

fun printTitle(colourPrinter: ColourPrinter) {
    colourPrinter.printBlue("=== ")
    colourPrinter.printRed("kar")
    colourPrinter.printGreen("tve")
    colourPrinter.printWhite("lang")
    colourPrinter.printlnBlue(" ===")
}