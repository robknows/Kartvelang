/*Created on 29/04/18. */
open class Text(private val text: String) {
    override fun toString(): String {
        return text
    }
}

object NullText : Text("")