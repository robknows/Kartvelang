/*Created on 29/04/18. */
open class TranslateQuestion(val english: String, val georgian: String) {
    fun markAnswer(attempt: String): Mark {
        val correct = georgian == attempt
        val diff = diffWord(attempt)
        return Mark(correct, diff, georgian)
    }

    // Assumption: attempt.length == answer.length
    private fun diffWord(attempt: String): Set<Int> {
        val indices = mutableSetOf<Int>()
        for (i in 0..(georgian.length - 1)) {
            if (georgian[i] != attempt[i]) {
                indices.add(i)
            }
        }
        return indices.toSet()
    }
}

object NullTranslateQuestion : TranslateQuestion("", "")
