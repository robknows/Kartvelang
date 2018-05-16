/*Created on 29/04/18. */
package logic

import java.util.*

open class Questions {
    val set: MutableList<Question> = mutableListOf()

    constructor()
    constructor(qs: Collection<Question>) {
        set.addAll(qs)
    }

    fun count(): Int {
        return set.count()
    }

    fun empty(): Boolean {
        return set.isEmpty()
    }

    fun add(question: Question) {
        set.add(question)
    }

    fun pop(): Question {
        return if (count() < 1) {
            NullTranslationQuestion
        } else {
            set.removeAt(0)
        }
    }

    fun insertDelayed(q: Question) {
        if (set.count() < 4) {
            add(q)
        } else {
            set.add(3, q)
        }
    }

    fun addAll(otherQuestions: Questions) {
        set.addAll(otherQuestions.set)
    }

    fun run(s: Screen, translationOverlay: TranslationOverlay, multipleChoiceOverlay: MultipleChoiceOverlay): QuestionsResults {
        var mistakes = 0
        var answered = 0
        val startTime = Calendar.getInstance().time.time
        while (!empty()) {
            val q = pop()

            val mark = when (q) {
                is TranslationQuestion -> translationOverlay.runQuestion(s, q)
                is MultipleChoiceQuestion -> multipleChoiceOverlay.runQuestion(s, q)
                else -> {
                    TODO("No overlay available for that question type")
                }
            }

            if (!mark.correct) {
                s.awaitCorrection(q)
                insertDelayed(q)
                mistakes++
            }

            s.awaitKeyPress(Key.ENTER)
            s.clear()
            answered++
        }
        val endTime = Calendar.getInstance().time.time
        return QuestionsResults((endTime - startTime).toDouble() / 1000, answered, mistakes)
    }
}

data class QuestionsResults(val runtime: Double, val qsAnswered: Int, val mistakes: Int)