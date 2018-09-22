/*Created on 04/05/18. */
package logic.question

import java.util.*

open class MultipleChoiceQuestion(val question: String, override val answer: String, val incorrect: Triple<String, String, String>, val answerChoice: MultipleChoiceChoice) : Question {
    override val fullCorrections: Boolean = false

    override fun verifyAnswer(attempt: String): Boolean {
        throw RuntimeException("MultipleChoiceQuestion.verifyAnswer: Shouldn't be called")
    }

    fun markAnswer(attempt: String): MultipleChoiceMark {
        val char = attempt.first().toLowerCase()
        if (!listOf('a', 'b', 'c', 'd').contains(char)) {
            return MultipleChoiceMark(false, MultipleChoiceChoice.NONE, answerChoice)
        } else {
            val choice = char.toChoice()
            return MultipleChoiceMark(choice == answerChoice, choice, answerChoice)
        }
    }
}

private fun Char.toChoice(): MultipleChoiceChoice {
    return when (this) {
        'a' -> MultipleChoiceChoice.A
        'b' -> MultipleChoiceChoice.B
        'c' -> MultipleChoiceChoice.C
        'd' -> MultipleChoiceChoice.D
        else -> MultipleChoiceChoice.NONE
    }
}

data class MultipleChoiceMark(override val correct: Boolean, val choice: MultipleChoiceChoice, val answer: MultipleChoiceChoice) : Mark

enum class MultipleChoiceChoice {
    A, B, C, D, NONE;
}

fun randomChoice(): MultipleChoiceChoice {
    return MultipleChoiceChoice.values()[Random().nextInt(4)]
}