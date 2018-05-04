class MultipleChoiceOverlay {
    var questionLine = Text("")
    var choices12 = Text("")
    var choices34 = Text("")

    override fun toString(): String {
        return questionLine.toString() + choices12.toLine() + choices34.toLine()
    }

    fun showQuestion(q: MultipleChoiceQuestion) {
        questionLine = Text("Which of these ${q.question}?")
        choices12 = Text("  ${q.answer}    ${q.incorrect.first}")
        choices34 = Text("  ${q.incorrect.second}    ${q.incorrect.third}")
    }
}
