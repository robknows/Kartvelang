class Module(val name: String) {
    val dependencies: MutableList<Module> = mutableListOf()

    fun addDependency(m: Module) {
        dependencies.add(m)
    }
}
