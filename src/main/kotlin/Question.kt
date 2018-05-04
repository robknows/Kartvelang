/*Created on 02/05/18. */
interface Question {
    val answer: String

    fun verifyAnswer(attempt: String): Boolean
}