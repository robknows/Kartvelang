/*Created on 10/05/18. */
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import logic.*
import org.json.JSONObject
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
    fun completedLessonDataSavesToEmptyUserProfile() {
        val u = User()

        val mockLesson = mock(Lesson::class.java)
        `when`(mockLesson.complete(mockScreen, spyTranslationOverlay, spyMultipleChoiceOverlay)).thenReturn(Lesson.LessonResults(50.0, 100.0))

        val t = Calendar.getInstance().time.time
        u.complete(mockLesson, mockScreen, spyTranslationOverlay, spyMultipleChoiceOverlay)

        assertEquals(1, u.totalLessonCompletions)
        assertEquals(1, u.dailyLessonCompletions)
        assertEquals(50.0, u.meanDailyAccuracy)
        assertEquals(100.0, u.lessonTime)
        assertTrue(t - u.lastCompletion < 50)
    }

    @Test
    fun completedLessonDataSavesToArbitraryUserProfile() {
        val u = User()
        u.totalLessonCompletions = 1
        u.dailyLessonCompletions = 1
        u.meanDailyAccuracy = 50.0
        u.lessonTime = 100.0
        u.lastCompletion = Calendar.getInstance().time.time - 100

        val mockLesson = mock(Lesson::class.java)
        `when`(mockLesson.complete(mockScreen, spyTranslationOverlay, spyMultipleChoiceOverlay)).thenReturn(Lesson.LessonResults(100.0, 80.0))

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

        val o = JSONObject()
        o.put("totalLessonCompletions", 1)
        o.put("dailyLessonCompletions", 1)
        o.put("meanDailyAccuracy", 50.0)
        o.put("lessonTime", 100.0)
        o.put("lastCompletion", t)

        val bufferedReader = File("user.json").inputStream().reader().buffered()
        val text = bufferedReader.readText()
        bufferedReader.close()
        Files.delete(Paths.get("user.json"))

        assertEquals("{\"meanDailyAccuracy\":50,\"lastCompletion\":$t,\"dailyLessonCompletions\":1,\"lessonTime\":100,\"totalLessonCompletions\":1}", text)
    }

    @Test
    fun canLoadUserToJSONFile() {
        val path = fp("src/test/resources/user.json")
        val uLoaded = User(path)

        assertEquals(1, uLoaded.totalLessonCompletions)
        assertEquals(1, uLoaded.dailyLessonCompletions)
        assertEquals(50.0, uLoaded.meanDailyAccuracy)
        assertEquals(100.0, uLoaded.lessonTime)
        assertEquals(1525986991184, uLoaded.lastCompletion)
    }

    @Test
    fun canCompleteMemoLesson() {
        val u = User()

        val mockMemoLesson = mock(MemoLesson::class.java)
        `when`(mockMemoLesson.complete(mockScreen, spyTranslationOverlay, spyMultipleChoiceOverlay)).thenReturn(Lesson.LessonResults(50.0, 100.0))

        val t = Calendar.getInstance().time.time
        u.complete(mockMemoLesson, mockScreen, spyTranslationOverlay, spyMultipleChoiceOverlay)

        assertEquals(1, u.totalLessonCompletions)
        assertEquals(1, u.dailyLessonCompletions)
        assertEquals(50.0, u.meanDailyAccuracy)
        assertEquals(100.0, u.lessonTime)
        assertTrue(t - u.lastCompletion < 50)
    }
}