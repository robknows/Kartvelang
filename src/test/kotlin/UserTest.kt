/*Created on 10/05/18. */
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import java.util.*

class UserTest {
    @Test
    fun completedLessonDataSavesToEmptyUserProfile() {
        val u = User()

        val mockLesson = mock(Lesson::class.java)
        `when`(mockLesson.complete()).thenReturn(Lesson.LessonResults(50.0, 100.0))

        val t = Calendar.getInstance().time.time
        u.complete(mockLesson)

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
        `when`(mockLesson.complete()).thenReturn(Lesson.LessonResults(100.0, 80.0))

        val t = Calendar.getInstance().time.time
        u.complete(mockLesson)

        assertEquals(2, u.totalLessonCompletions)
        assertEquals(2, u.dailyLessonCompletions)
        assertEquals(75.0, u.meanDailyAccuracy)
        assertEquals(180.0, u.lessonTime)
        assertTrue(t - u.lastCompletion < 50)
    }

    @Test
    fun canSaveAndLoadUserToJSONFile() {
        val uSaved = User()
        uSaved.totalLessonCompletions = 1
        uSaved.dailyLessonCompletions = 1
        uSaved.meanDailyAccuracy = 50.0
        uSaved.lessonTime = 100.0
        uSaved.lastCompletion = Calendar.getInstance().time.time

        uSaved.saveProfile("user.json")
        val uLoaded = User("user.json")

        assertEquals(uSaved.totalLessonCompletions, uLoaded.totalLessonCompletions)
        assertEquals(uSaved.dailyLessonCompletions, uLoaded.dailyLessonCompletions)
        assertEquals(uSaved.meanDailyAccuracy, uLoaded.meanDailyAccuracy)
        assertEquals(uSaved.lessonTime, uLoaded.lessonTime)
        assertEquals(uSaved.lastCompletion, uLoaded.lastCompletion)
    }
}