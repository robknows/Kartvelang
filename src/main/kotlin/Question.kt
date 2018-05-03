/*Created on 02/05/18. */
interface Question<MarkType, OverlayType> {
    val answer: String

    fun markAnswer(attempt: String): MarkType
    fun complete(s: Screen, o: OverlayType): Boolean
}