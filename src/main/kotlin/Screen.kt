class Screen {
    var text: String = ""

    override fun toString(): String {
        return text
    }

    fun showQuestion(q: Question) {
        text = q.questionText
    }
}