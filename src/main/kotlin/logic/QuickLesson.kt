/*Created on 30/04/18. */
package logic

import java.util.*

open class QuickLesson(val qs: Questions) {
    open fun complete(s: Screen, translationOverlay: TranslationOverlay, multipleChoiceOverlay: MultipleChoiceOverlay): LessonResults {
        val (lessonTime, answered, mistakes) = qs.run(s, translationOverlay, multipleChoiceOverlay)
        s.closeInput()
        val results = LessonResults(
                100 * (answered - mistakes).toDouble() / answered, // accuracyPc
                lessonTime)                                        // timeSeconds
        s.showPostLessonInfo(results.accuracyPc, results.timeSeconds, randomHint())
        s.print()
        s.clear()
        return results
    }
}

data class LessonResults(val accuracyPc: Double, val timeSeconds: Double)

fun <E> List<E>.randomChoice(): E {
    return get(Random().nextInt(size))
}

fun randomHint(): String {
    val hints = listOf(
            "All nominative nouns in Georgian end in a vowel",
            "Georgian has no long vowels",
            "Nouns whose root ends in a consonant have \"ი\" added to the end in the nominative case",
            "Georgian uses postpositions rather than prepositions")
    return hints.randomChoice()
}