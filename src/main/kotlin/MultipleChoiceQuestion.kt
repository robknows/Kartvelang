import MultipleChoiceChoice.*

class MultipleChoiceQuestion(val question: String, override val answer: String, val incorrect: Triple<String, String, String>) : Question {
    override fun verifyAnswer(attempt: String): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun markAnswer(attempt: String): MultipleChoiceMark {
        val choice = when (attempt) {
            answer -> A
            incorrect.first -> B
            incorrect.second -> C
            incorrect.third -> D
            else -> {
                TODO("This shouldn't happen")
            }
        }
        return MultipleChoiceMark(attempt == answer, choice)
    }
}

data class MultipleChoiceMark(override val correct: Boolean, val choice: MultipleChoiceChoice) : Mark

enum class MultipleChoiceChoice {
    A, B, C, D
}
