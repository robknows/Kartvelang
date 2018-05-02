class MultipleChoiceQuestion(val question: String, override val answer: String, val wrong: Triple<String, String, String>) : Question<MultipleChoiceMark> {
    override fun markAnswer(attempt: String): MultipleChoiceMark {
        return MultipleChoiceMark(attempt == answer)
    }

    override fun complete(s: Screen, q: Question<MultipleChoiceMark>): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

data class MultipleChoiceMark(override val correct: Boolean) : Mark