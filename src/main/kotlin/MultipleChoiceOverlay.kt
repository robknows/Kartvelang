class MultipleChoiceOverlay {
    var questionLine = Text("")
    var choice1 = Text("")
    var choice2 = Text("")
    var choice3 = Text("")
    var choice4 = Text("")

    override fun toString(): String {
        return "$questionLine\n  $choice1    $choice2\n  $choice3    $choice4"
    }

    fun showQuestion(q: MultipleChoiceQuestion) {
        questionLine = Text("Which of these ${q.question}?")
        choice1 = Text(q.answer)
        choice2 = Text(q.incorrect.first)
        choice3 = Text(q.incorrect.second)
        choice4 = Text(q.incorrect.third)
    }

    fun showAnswer(a: String) {
        when (a) {
            choice1.toString() -> choice1.setAllRed()
            choice2.toString() -> choice2.setAllRed()
            choice3.toString() -> choice3.setAllRed()
            choice4.toString() -> choice4.setAllRed()
            else -> {
            } // This should not happen
        }
    }
}
