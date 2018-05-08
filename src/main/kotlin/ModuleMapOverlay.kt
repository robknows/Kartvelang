import kotlin.math.max

/*Created on 08/05/18. */
class ModuleMapOverlay : Overlay {
    private val topLine = Text("Available Modules:")

    override fun printWith(printer: ColourPrinter) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clear() {
        modules.clear()
    }

    override fun maxLineLength(): Int {
        val indentSize = 5
        return max(topLine.toString().count(), modules.map({ m -> m.name.count() + indentSize }).max() ?: 0)
    }

    val modules = mutableListOf<Module>()

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
