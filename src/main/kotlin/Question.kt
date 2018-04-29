open class Question(val questionText: String, val answerText: String) {
    fun markAnswer(attempt: String): Mark {
        val correct = answerText == attempt
        return Mark(correct)
    }
}

object NullQuestion : Question("", "")
