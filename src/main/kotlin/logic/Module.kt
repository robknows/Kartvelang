/*Created on 08/05/18. */
package logic

class Module(val name: String) {
    val dependencies: MutableList<Module> = mutableListOf()
    var questions: Questions = Questions()

    fun addDependency(m: Module) {
        dependencies.add(m)
    }

    fun addQuestions(qs: Questions) {
        questions.addAll(qs)
    }
}
