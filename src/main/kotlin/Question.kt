/*Created on 02/05/18. */
interface Question<MarkType> {
    val answer: String

    fun markAnswer(attempt: String): MarkType
    fun complete(s: Screen, q: Question<MarkType>): Boolean
}