/*Created on 30/04/18. */
import java.util.*

class Lesson(val s: Screen, val qs: Questions) {
    fun start() {
        val startTime = Calendar.getInstance().time.time
        while (!qs.empty()) {
            val q = qs.pop()
            s.showQuestion(q)
            s.print()
            val a = s.awaitAnswer().toString()
            s.showAnswer(a)
            val mark = q.markAnswer(a)
            if (mark.correct) {
                s.showAnswerCorrect()
                s.print()
            } else {
                val errorIndices = mark.errorIndices
                s.showAnswerIncorrectIndices(errorIndices)
                s.showCorrection(q.answerText, errorIndices)
                s.print()
                s.awaitCorrection(q.answerText)
            }
            if (!mark.correct) {
                qs.insertDelayed(q)
            }
            s.awaitKeyPress(Key.ENTER)
            s.clear()
        }
        val endTime = Calendar.getInstance().time.time
        s.showLessonDuration(((endTime - startTime).toDouble() / 1000))
        s.print()
        s.clear()
        s.close()
    }
}
