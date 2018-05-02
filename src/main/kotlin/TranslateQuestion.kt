/*Created on 29/04/18. */
import kotlin.math.max

open class TranslateQuestion(val given: String, val answer: String) {
    fun markAnswer(attempt: String): Mark {
        @Suppress("NAME_SHADOWING")
        val attempt = prepareForMarking(attempt)
        val answer  = prepareForMarking(answer)
        val correct = answer == attempt
        val diff = if (answer.length == attempt.length) {
            diffWord(answer, attempt)
        } else {
            (0 until max(attempt.length, answer.length)).toSet()
        }
        return Mark(correct, diff, answer)
    }

    // Assumption: answer.length == attempt.length
    private fun diffWord(answer: String, attempt: String): Set<Int> {
        val indices = mutableSetOf<Int>()
        for (i in 0 until answer.length) {
            if (answer[i] != attempt[i]) {
                indices.add(i)
            }
        }
        return indices.toSet()
    }

    fun flipped(): TranslateQuestion {
        return TranslateQuestion(answer, given)
    }
}

private fun prepareForMarking(s: String): String {
    return s.trim({ c -> c.isPunctuation() or c.isWhitespace() }).filter({ c -> !c.isPunctuation() }).despace()
}

private fun String.despace(): String {
    return split(" ").filter({ l -> !l.isEmpty() }).reduce({ acc, nxt -> "$acc $nxt" })
}

private fun Char.isPunctuation(): Boolean {
    return toChar() in setOf('?', ',', '.')
}

object NullTranslateQuestion : TranslateQuestion("", "")
