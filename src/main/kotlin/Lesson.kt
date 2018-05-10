/*Created on 30/04/18. */
import java.util.*

open class Lesson(val s: Screen, val qs: Questions, val translationOverlay: TranslationOverlay, val multipleChoiceOverlay: MultipleChoiceOverlay) {
    open fun complete(): LessonResults {
        var mistakes = 0
        var answered = 0
        val startTime = Calendar.getInstance().time.time
        while (!qs.empty()) {
            val q = qs.pop()

            val mark = when (q) {
                is TranslationQuestion -> translationOverlay.runQuestion(s, q)
                is MultipleChoiceQuestion -> multipleChoiceOverlay.runQuestion(s, q)
                else -> {
                    TODO("No overlay available for that question type")
                }
            }

            if (!mark.correct) {
                s.awaitCorrection(q)
                qs.insertDelayed(q)
                mistakes++
            }

            s.awaitKeyPress(Key.ENTER)
            s.clear()
            answered++
        }
        val endTime = Calendar.getInstance().time.time
        s.close()
        val results = LessonResults(
                100 * (answered - mistakes).toDouble() / answered, // accuracyPc
                (endTime - startTime).toDouble() / 1000)           // timeSeconds
        s.showPostLessonInfo(results.accuracyPc, results.timeSeconds, randomHint())
        s.print()
        s.clear()
        return results
    }

    data class LessonResults(val accuracyPc: Double, val timeSeconds: Double)
}

fun randomHint(): String {
    val hints = listOf(
            "All nominative nouns in Georgian end in a vowel",
            "Georgian has no long vowels",
            "Nouns whose root ends in a consonant have \"ი\" added to the end in the nominative case",
            "Georgian uses postpositions rather than prepositions")
    return hints.randomChoice()
}

fun <E> List<E>.randomChoice(): E {
    return get(Random().nextInt(size))
}
