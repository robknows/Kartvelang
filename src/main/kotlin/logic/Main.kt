/*Created on 29/04/18. */
@file:JvmName("MainClass")

package logic

import logic.io.ColourPrinter
import logic.io.Screen
import java.io.BufferedReader
import java.io.InputStreamReader

fun main(args: Array<String>) {
    val printer = ColourPrinter()
    val input = BufferedReader(InputStreamReader(System.`in`))
    val screen = Screen(printer, input)
    val user = User()

    val app = Kartvelang(screen, user)

    app.run()

    System.exit(0)
}

