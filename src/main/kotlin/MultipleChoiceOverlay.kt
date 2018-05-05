class MultipleChoiceOverlay : Overlay<MultipleChoiceQuestion, MultipleChoiceMark> {
    var questionLine = Text("")
    var choice1 = Text("")
    var choice2 = Text("")
    var choice3 = Text("")
    var choice4 = Text("")

    override fun printWith(printer: ColourPrinter) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showQuestion(q: MultipleChoiceQuestion) {
        questionLine = Text("Which of these ${q.question}?")
        choice1 = Text(q.answer)
        choice2 = Text(q.incorrect.first)
        choice3 = Text(q.incorrect.second)
        choice4 = Text(q.incorrect.third)
    }

    override fun showAnswer(a: String) {
    }

    override fun showMarkedAnswer(m: MultipleChoiceMark) {
        if (m.correct) {
            choice1.setAllGreen()
        } else {
            when (m.choice) {
                MultipleChoiceChoice.B -> choice2.setAllRed()
                MultipleChoiceChoice.C -> choice3.setAllRed()
                MultipleChoiceChoice.D -> choice4.setAllRed()
                MultipleChoiceChoice.A -> TODO("This shouldn't happen")
            }
            choice1.setAllBlue()
        }
    }

    override fun clear() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun maxLineLength(): Int {
        return listOf(questionLine.toString().length,
                6 + choice1.toString().length + choice2.toString().length,
                6 + choice3.toString().length + choice4.toString().length).max() ?: 0
    }

    override fun runQuestion(s: Screen, q: MultipleChoiceQuestion): MultipleChoiceMark {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun toString(): String {
        return "$questionLine\n  $choice1    $choice2\n  $choice3    $choice4"
    }
}
