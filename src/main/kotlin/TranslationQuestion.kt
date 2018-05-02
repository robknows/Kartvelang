/*Created on 29/04/18. */
import kotlin.math.max

open class TranslationQuestion(val given: String, val answer: String) {
    fun markAnswer(attempt: String): TranslationMark {
        @Suppress("NAME_SHADOWING")
        val attempt = prepareForMarking(attempt)
        val answer  = prepareForMarking(answer)
        val correct = answer == attempt
        val diff = if (answer.length == attempt.length) {
            diffWord(answer, attempt)
        } else {
            (0 until max(attempt.length, answer.length)).toSet()
        }
        return TranslationMark(correct, diff, answer)
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

    fun flipped(): TranslationQuestion {
        return TranslationQuestion(answer, given)
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

object NullTranslationQuestion : TranslationQuestion("", "")

data class TranslationMark(val correct: Boolean, val errorIndices: Set<Int>, val correctAnswer: String)
