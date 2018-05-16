/*Created on 16/05/18. */
package logic

interface Lesson {
    fun complete(s: Screen, translationOverlay: TranslationOverlay, multipleChoiceOverlay: MultipleChoiceOverlay): LessonResults
}