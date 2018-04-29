class Questions {
    val set: MutableList<Question> = mutableListOf()

    fun add(question: Question) {
        set.add(question)
    }

    fun count(): Int {
        return set.count()
    }
}
