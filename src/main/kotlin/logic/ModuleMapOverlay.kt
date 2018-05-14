/*Created on 08/05/18. */
package logic

import kotlin.math.max

class ModuleMapOverlay : Overlay {
    private val topLine = Text("Available Modules:")
    val modules = mutableListOf<Module>()

    override fun printWith(printer: ColourPrinter) {
        topLine.printlnWith(printer)
        for (m in modules) {
            printer.printlnWhite("\t-${m.name}")
        }
    }

    override fun clear() {
        modules.clear()
    }

    override fun maxLineLength(): Int {
        val indentSize = 5
        return max(topLine.toString().count(), modules.map({ m -> m.name.count() + indentSize }).max() ?: 0)
    }

    override fun toString(): String {
        val header = topLine.toString() + "\n"
        if (modules.isEmpty()) {
            return header
        } else {
            return header + modules.map({ m -> "\t-${m.name}" }).reduce({ acc, nxt -> "$acc\n$nxt" })
        }
    }

    fun showModule(m: Module) {
        modules.add(m)
    }
}
