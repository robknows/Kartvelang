import MultipleChoiceChoice.*

open class MultipleChoiceQuestion(val question: String, override val answer: String, val incorrect: Triple<String, String, String>) : Question {
    override val fullCorrections: Boolean = false

    override fun verifyAnswer(attempt: String): Boolean {
        TODO("should not be called")
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
