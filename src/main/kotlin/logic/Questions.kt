/*Created on 29/04/18. */
package logic

class Questions {
    val set: MutableList<Question> = mutableListOf()

    constructor()
    constructor(qs: Collection<Question>) {
        set.addAll(qs)
    }

    fun count(): Int {
        return set.count()
    }

    fun empty(): Boolean {
        return set.isEmpty()
    }

    fun add(question: Question) {
        set.add(question)
    }

    fun pop(): Question {
        return if (count() < 1) {
            NullTranslationQuestion
        } else {
            set.removeAt(0)
        }
    }

    fun insertDelayed(q: Question) {
        if (set.count() < 4) {
            add(q)
        } else {
            set.add(3, q)
        }
    }
}