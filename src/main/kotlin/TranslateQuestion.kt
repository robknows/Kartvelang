/*Created on 29/04/18. */
open class TranslateQuestion(val given: String, val answer: String) {
    fun markAnswer(attempt: String): Mark {
        val correct = answer == attempt
        val diff = if (answer.length == attempt.length) {
            diffWord(attempt)
        } else {
            (0..(attempt.length - 1)).toSet()
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

object NullTranslateQuestion : TranslateQuestion("", "")
