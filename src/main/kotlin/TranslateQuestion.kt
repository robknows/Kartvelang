import kotlin.math.max

/*Created on 29/04/18. */
open class TranslateQuestion(val given: String, val answer: String) {
    fun markAnswer(attempt: String): Mark {
        val trimmedAttempt = attempt.trim().filter({ char -> !char.isPunctuation() })
        val correct = answer == trimmedAttempt
        val diff = if (answer.length == trimmedAttempt.length) {
            diffWord(trimmedAttempt)
        } else {
            (0..(max(trimmedAttempt.length, answer.length) - 1)).toSet()
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
