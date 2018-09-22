/*Created on 29/04/18. */
package logic.question

import kotlin.math.max

open class TranslationQuestion(val given: String, override val answer: String) : Question {
    override val fullCorrections: Boolean = true

    fun markAnswer(attempt: String): TranslationMark {
        @Suppress("NAME_SHADOWING")
        val attempt = homogenise(attempt)
        val answer = homogenise(answer)
        val correct = answer == attempt
        val diff = if (answer.length == attempt.length) {
            diffWord(answer, attempt)
        } else {
            (0 until max(attempt.length, answer.length)).toSet()
        }
        return TranslationMark(correct, diff, answer)
    }

    override fun verifyAnswer(attempt: String): Boolean {
        return markAnswer(attempt).correct
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

fun homogenise(s: String): String {
    return s.trim({ c -> c.isPunctuation() or c.isWhitespace() }).filter({ c -> !c.isPunctuation() }).despace().toLowerCase()
}

private fun String.despace(): String {
    return split(" ").filter({ l -> !l.isEmpty() }).reduce({ acc, nxt -> "$acc $nxt" })
}

private fun Char.isPunctuation(): Boolean {
    return toChar() in setOf('?', ',', '.')
}

data class TranslationMark(override val correct: Boolean, val errorIndices: Set<Int>, val correctAnswer: String) : Mark

object NullTranslationQuestion : TranslationQuestion("", "")