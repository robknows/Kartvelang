/*Created on 08/05/18. */
class ModuleMapOverlay {
    private val topLine = Text("Available Modules:")
    val modules = mutableListOf<Module>()

    override fun toString(): String {
        return topLine.toString() + "\n\t" + modules.map({ m -> "-${m.name}" }).reduce({ acc, nxt -> "$acc\n\t$nxt" })
    }

    fun showModule(m: Module) {
        modules.add(m)
    }
}
