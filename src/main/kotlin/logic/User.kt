/*Created on 10/05/18. */
package logic

import course.lesson_hello
import logic.io.Screen
import logic.language.concat
import logic.lesson.Lesson
import logic.overlay.MultipleChoiceOverlay
import logic.overlay.TranslationOverlay
import org.json.JSONObject
import java.io.File
import java.io.FileWriter
import java.util.*
import kotlin.collections.HashMap

open class User {
    var totalLessonCompletions: Int = 0
    var dailyLessonCompletions: Int = 0
    var meanDailyAccuracy: Double = 0.0
    var lessonTime: Double = 0.0
    var lastCompletion: Long = 0L
    private val completedLessonData: HashMap<String, CompletedLessonData> = HashMap()
    lateinit var profileFile: String

    constructor()
    constructor(filename: String) {
        val bufferedReader = File(filename).inputStream().reader().buffered()
        val text = bufferedReader.readText()
        bufferedReader.close()

        val uJSON = JSONObject(text)
        totalLessonCompletions = uJSON.getInt("totalLessonCompletions")
        dailyLessonCompletions = uJSON.getInt("dailyLessonCompletions")
        meanDailyAccuracy = uJSON.getDouble("meanDailyAccuracy")
        lessonTime = uJSON.getDouble("lessonTime")
        lastCompletion = uJSON.getLong("lastCompletion")
        profileFile = filename
    }

    fun saveProfile(filename: String) {
        profileFile = filename
        saveProfile()
    }

    fun saveProfile() {
        val o = JSONObject()
        o.put("totalLessonCompletions", totalLessonCompletions)
        o.put("dailyLessonCompletions", dailyLessonCompletions)
        o.put("meanDailyAccuracy", meanDailyAccuracy)
        o.put("lessonTime", lessonTime)
        o.put("lastCompletion", lastCompletion)

        FileWriter(profileFile).use { file ->
            file.write(o.toString())
        }
    }

    fun complete(lesson: Lesson, s: Screen, translationOverlay: TranslationOverlay, multipleChoiceOverlay: MultipleChoiceOverlay) {
        val results = lesson.complete(s, translationOverlay, multipleChoiceOverlay)
        lastCompletion = Calendar.getInstance().time.time
        meanDailyAccuracy = (results.accuracyPc + (meanDailyAccuracy * dailyLessonCompletions)) / (dailyLessonCompletions + 1)
        dailyLessonCompletions++
        totalLessonCompletions++
        lessonTime += results.timeSeconds
        completedLessonData[lesson.name] = CompletedLessonData(lesson, 100.0)
    }

    fun strength(lesson: Lesson): Double {
        return completedLessonData[lesson.name]!!.strength
    }

    open fun availableLessons(): List<Lesson> {
        // TODO: Make this use some lesson dependency tree like Duolingo
        return concat(listOf(lesson_hello), completedLessonData.values.map(CompletedLessonData::lesson))
    }
}

data class CompletedLessonData(val lesson: Lesson, val strength: Double)
