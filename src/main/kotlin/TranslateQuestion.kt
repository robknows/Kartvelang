/*Created on 29/04/18. */
import kotlin.math.max

open class TranslateQuestion(val given: String, val answer: String) {
    fun markAnswer(attempt: String): Mark {
        @Suppress("NAME_SHADOWING")
        val attempt = attempt.trim({ c -> c.isPunctuation() or c.isWhitespace() }).filter({ c -> !c.isPunctuation() })
        val correct = answer == attempt
        val diff = if (answer.length == attempt.length) {
            diffWord(attempt)
        } else {
            (0..(max(attempt.length, answer.length) - 1)).toSet()
        }
        return Mark(correct, diff, answer)
    }

    // Assumption: attempt.length == answer.length
    private fun diffWord(attempt: String): Set<Int> {
        val indices = mutableSetOf<Int>()
        for (i in 0..(answer.length - 1)) {
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

private fun Char.isPunctuation(): Boolean {
    return toChar() in setOf('?', ',', '.')
}

object NullTranslateQuestion : TranslateQuestion("", "")
