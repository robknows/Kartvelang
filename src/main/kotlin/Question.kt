/*Created on 02/05/18. */
interface Question<Mark, Overlay> {
    val answer: String

    fun complete(s: Screen, o: Overlay): Mark
}