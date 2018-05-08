/*Created on 08/05/18. */
class Module(val name: String) {
    val dependencies: MutableList<Module> = mutableListOf()

    fun addDependency(m: Module) {
        dependencies.add(m)
    }
}
