/*Created on 10/05/18. */
package unit

import course.lesson_hello
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import logic.User
import logic.io.Screen
import logic.lesson.Lesson
import logic.lesson.LessonResults
import logic.overlay.MultipleChoiceOverlay
import logic.overlay.TranslationOverlay
import org.junit.Test
import org.mockito.Mockito.*
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

class UserTest {
    val mockScreen = mock(Screen::class.java)
    val spyTranslationOverlay = spy(TranslationOverlay())
    val spyMultipleChoiceOverlay = spy(MultipleChoiceOverlay())

    @Test
    fun initialisedWithEmptyStats() {
        val u = User()
        assertEquals(0, u.totalLessonCompletions)
        assertEquals(0, u.dailyLessonCompletions)
        assertEquals(0.0, u.meanDailyAccuracy)
        assertEquals(0.0, u.lessonTime)
        assertEquals(0L, u.lastCompletion)
    }

    @Test
    fun canCompleteFirstLesson() {
        val u = User()

        val mockLesson = mock(Lesson::class.java)
        `when`(mockLesson.complete(mockScreen, spyTranslationOverlay, spyMultipleChoiceOverlay)).thenReturn(LessonResults(50.0, 100.0))

        val t = Calendar.getInstance().time.time
        u.complete(mockLesson, mockScreen, spyTranslationOverlay, spyMultipleChoiceOverlay)

        assertEquals(1, u.totalLessonCompletions)
        assertEquals(1, u.dailyLessonCompletions)
        assertEquals(50.0, u.meanDailyAccuracy)
        assertEquals(100.0, u.lessonTime)
        assertTrue(t - u.lastCompletion < 50)
    }

    @Test
    fun canCompleteSubsequentLessons() {
        val u = User()
        u.totalLessonCompletions = 1
        u.dailyLessonCompletions = 1
        u.meanDailyAccuracy = 50.0
        u.lessonTime = 100.0
        u.lastCompletion = Calendar.getInstance().time.time - 100

        val mockLesson = mock(Lesson::class.java)
        `when`(mockLesson.complete(mockScreen, spyTranslationOverlay, spyMultipleChoiceOverlay)).thenReturn(LessonResults(100.0, 80.0))

        val t = Calendar.getInstance().time.time
        u.complete(mockLesson, mockScreen, spyTranslationOverlay, spyMultipleChoiceOverlay)

        assertEquals(2, u.totalLessonCompletions)
        assertEquals(2, u.dailyLessonCompletions)
        assertEquals(75.0, u.meanDailyAccuracy)
        assertEquals(180.0, u.lessonTime)
        assertTrue(t - u.lastCompletion < 50)
    }

    @Test
    fun canSaveUserToJSONFile() {
        val uSaved = User()
        val t = Calendar.getInstance().time.time
        uSaved.totalLessonCompletions = 1
        uSaved.dailyLessonCompletions = 1
        uSaved.meanDailyAccuracy = 50.0
        uSaved.lessonTime = 100.0
        uSaved.lastCompletion = t

        uSaved.saveProfile("user.json")

        val bufferedReader = File("user.json").inputStream().reader().buffered()
        val text = bufferedReader.readText()
        bufferedReader.close()
        Files.delete(Paths.get("user.json"))

        assertEquals("{\"meanDailyAccuracy\":50,\"lastCompletion\":$t,\"dailyLessonCompletions\":1,\"lessonTime\":100,\"totalLessonCompletions\":1}", text)
    }

    @Test
    fun canLoadUserFromJSONFile() {
        val path = fp("src/test/resources/user.json")
        val uLoaded = User(path)

        assertEquals(1, uLoaded.totalLessonCompletions)
        assertEquals(1, uLoaded.dailyLessonCompletions)
        assertEquals(50.0, uLoaded.meanDailyAccuracy)
        assertEquals(100.0, uLoaded.lessonTime)
        assertEquals(1525986991184, uLoaded.lastCompletion)
    }

    @Test
    fun completingALessonAssignsProgressToThatLesson() {
        val u = User()

        val mockLesson = mock(Lesson::class.java)
        `when`(mockLesson.complete(mockScreen, spyTranslationOverlay, spyMultipleChoiceOverlay)).thenReturn(LessonResults(50.0, 100.0))

        u.complete(mockLesson, mockScreen, spyTranslationOverlay, spyMultipleChoiceOverlay)

        assertEquals(100.0, u.strength(mockLesson))
    }

    @Test
    fun currentlyAvailableLessonsAreThoseThatAreInitiallyAvailableOrCompleted() {
        val u = User()

        assertEquals(setOf(lesson_hello), u.availableLessons(setOf()))
    }

    @Test
    fun completingALessonMakesItAvailable() {
        val u = User()
        val mockScreen = mock(Screen::class.java)

        val mockLesson = mock(Lesson::class.java)
        `when`(mockLesson.complete(mockScreen, spyTranslationOverlay, spyMultipleChoiceOverlay)).thenReturn(LessonResults(100.0, 75.0))
        u.complete(mockLesson, mockScreen, spyTranslationOverlay, spyMultipleChoiceOverlay)

        assertEquals(setOf(lesson_hello, mockLesson), u.availableLessons(setOf()))
    }


    @Test
    fun completingALessonCausesDependentLessonsToBecomeAvailable() {
        val u = User()
        val mockScreen = mock(Screen::class.java)

        val dependencyLesson = object : Lesson {
            override val name: String = "dependency"
            override val dependencies: List<Lesson> = listOf()

            override fun complete(s: Screen, translationOverlay: TranslationOverlay, multipleChoiceOverlay: MultipleChoiceOverlay): LessonResults {
                return LessonResults(100.0, 75.0)
            }

            override fun countQuestions(): Int {
                throw RuntimeException("completingALessonCausesDependentLessonsToBecomeAvailable.dependencyLesson.countQuestions: Shouldn't get called")
            }

        }

        val dependingLesson = object : Lesson {
            override val name = "dependent"
            override val dependencies = listOf(dependencyLesson)

            override fun complete(s: Screen, translationOverlay: TranslationOverlay, multipleChoiceOverlay: MultipleChoiceOverlay): LessonResults {
                throw RuntimeException("completingALessonCausesDependentLessonsToBecomeAvailable.dependingLesson.complete: Shouldn't get called")
            }

            override fun countQuestions(): Int {
                throw RuntimeException("completingALessonCausesDependentLessonsToBecomeAvailable.dependingLesson.countQuestions: Shouldn't get called")
            }
        }

        u.complete(dependencyLesson, mockScreen, spyTranslationOverlay, spyMultipleChoiceOverlay)

        assertEquals(setOf(lesson_hello, dependencyLesson, dependingLesson), u.availableLessons(setOf(dependencyLesson, dependingLesson)))
    }
}