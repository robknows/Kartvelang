/*Created on 10/05/18. */
import java.util.*

class User {
    var totalLessonCompletions: Int = 0
    var dailyLessonCompletions: Int = 0
    var meanDailyAccuracy: Double = 0.0
    var lessonTime: Double = 0.0
    var lastCompletion: Long = 0L

    fun complete(lesson: Lesson) {
        val results = lesson.complete()
        lastCompletion = Calendar.getInstance().time.time
        meanDailyAccuracy = (results.accuracyPc + (meanDailyAccuracy * dailyLessonCompletions)) / (dailyLessonCompletions + 1)
        dailyLessonCompletions++
        totalLessonCompletions++
        lessonTime += results.timeSeconds
    }
}
