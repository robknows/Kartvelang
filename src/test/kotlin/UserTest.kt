/*Created on 10/05/18. */
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import java.util.*

class UserTest {
    @Test
    fun completedLessonDataSavesToUserProfile() {
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
}