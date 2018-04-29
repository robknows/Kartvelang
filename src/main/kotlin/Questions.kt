/*Created on 29/04/18. */
class Questions {
    val set: MutableList<Question> = mutableListOf()

    fun add(question: Question) {
        set.add(question)
    }

    fun count(): Int {
        return set.count()
    }

    fun pop(): Question {
        return if (count() < 1) {
            NullQuestion
        } else {
            set.removeAt(0)
        }
    }

    fun empty(): Boolean {
        return set.isEmpty()
    }
}
