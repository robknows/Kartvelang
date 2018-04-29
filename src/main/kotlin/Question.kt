open class Question(question: String, answer: String) {
    val questionText: String = question
}

object NullQuestion : Question("", "")
