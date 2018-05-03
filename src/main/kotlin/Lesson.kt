/*Created on 30/04/18. */
import java.util.*

class Lesson(val s: Screen, val qs: Questions) {
    val translationOverlay = TranslationOverlay()

    fun complete(): LessonResults {
        var mistakes = 0
        var answered = 0
        val startTime = Calendar.getInstance().time.time
        while (!qs.empty()) {
            val q = qs.pop()

            val correct = q.complete(s, translationOverlay)

            if (!correct) {
                s.awaitCorrection(q.answer)
                qs.insertDelayed(q)
                mistakes++
            }

            s.awaitKeyPress(Key.ENTER)
            s.clear()
            answered++
        }
        val endTime = Calendar.getInstance().time.time
        s.close()
        return LessonResults(
                100 * (answered - mistakes).toDouble() / answered, // accuracyPc
                (endTime - startTime).toDouble() / 1000)           // timeSeconds
    }

    data class LessonResults(val accuracyPc: Double, val timeSeconds: Double)
}
