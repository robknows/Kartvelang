/*Created on 29/04/18. */
open class Question(val questionText: String, val answerText: String) {
    fun markAnswer(attempt: String): Mark {
        val correct = answerText == attempt
        val diff = diffWord(attempt)
        return Mark(correct, diff)
    }

    // Assumption: attempt.length == answerText.length
    private fun diffWord(attempt: String): Set<Int> {
        val indices = mutableSetOf<Int>()
        for (i in 0..(answerText.length - 1)) {
            if (answerText[i] != attempt[i]) {
                indices.add(i)
            }
        }
        return indices.toSet()
    }
}

object NullQuestion : Question("", "")
