class MultipleChoiceQuestion(val question: String, val answer: String, val wrong: Triple<String, String, String>) {
    fun markAnswer(attempt: String): MultipleChoiceMark {
        return MultipleChoiceMark(attempt == answer)
    }
}

data class MultipleChoiceMark(val correct: Boolean)