/*Created on 30/04/18. */
import java.util.*

class Lesson(val s: Screen, val qs: Questions) {
    fun complete(): LessonResults {
        var mistakes = 0
        var answered = 0
        val startTime = Calendar.getInstance().time.time
        while (!qs.empty()) {
            val q = qs.pop()
            s.showTranslateQuestion(q)
            s.print()
            val a = s.awaitAnswer().toString()
            s.showAnswer(a)
            val mark = q.markAnswer(a)
            s.showMarkedAnswer(mark)
            if (!mark.correct) {
                qs.insertDelayed(q)
                s.awaitCorrection(q.answer)
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
