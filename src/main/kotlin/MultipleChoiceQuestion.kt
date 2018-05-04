class MultipleChoiceQuestion(val question: String, override val answer: String, val incorrect: Triple<String, String, String>) : Question {
    override fun verifyAnswer(attempt: String): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun markAnswer(attempt: String): MultipleChoiceMark {
        return MultipleChoiceMark(attempt == answer)
    }
}

data class MultipleChoiceMark(override val correct: Boolean) : Mark
