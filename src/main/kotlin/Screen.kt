import java.io.BufferedReader

class Screen {
    var text: String = ""

    override fun toString(): String {
        return text
    }

    fun showQuestion(q: Question) {
        text = q.questionText
    }

    fun awaitAnswer(source: BufferedReader): Text {
        val readLine = source.readLine()
        return if (readLine == null) {
            NullText
        } else {
            Text(readLine)
        }
    }
}