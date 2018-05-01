/*Created on 29/04/18. */
open class TranslateQuestion(val given: String, val answer: String) {
    fun markAnswer(attempt: String): Mark {
        val correct = answer == attempt
        val diff = diffWord(attempt)
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
}

object NullTranslateQuestion : TranslateQuestion("", "")
