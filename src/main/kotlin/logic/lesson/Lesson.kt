/*Created on 16/05/18. */
package logic.lesson

import logic.io.Screen
import logic.overlay.MultipleChoiceOverlay
import logic.overlay.TranslationOverlay
import logic.question.Question

interface Lesson {
    val name: String
    val dependencies: List<Lesson>

    fun complete(s: Screen, translationOverlay: TranslationOverlay, multipleChoiceOverlay: MultipleChoiceOverlay): LessonResults

    fun countQuestions(): Int

    fun completeStage(qs: List<Question>, s: Screen, translationOverlay: TranslationOverlay, multipleChoiceOverlay: MultipleChoiceOverlay): QuestionsResults {
        return if (qs.isEmpty()) {
            QuestionsResults(0.0, 0, 0)
        } else {
            Questions(qs).run(s, translationOverlay, multipleChoiceOverlay)
        }
    }
}