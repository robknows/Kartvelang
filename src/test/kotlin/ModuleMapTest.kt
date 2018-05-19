/*Created on 17/05/18. */
import course.lesson_hello
import course.lesson_whatareyoucalled
import junit.framework.TestCase.assertEquals
import logic.ModuleMap
import logic.User
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.spy

class ModuleMapTest {
    @Test
    fun canGetAvailableLessons() {
        val mm = ModuleMap(lesson_hello, lesson_whatareyoucalled)

        assertEquals(listOf(lesson_hello, lesson_whatareyoucalled), mm.lessons)
    }

    @Test
    fun canGetListOfAvailableLessonsForUser() {
        val mm = ModuleMap(lesson_hello, lesson_whatareyoucalled)
        val u = spy(User())
        `when`(u.hasAccessTo(lesson_hello)).thenReturn(true)
        `when`(u.hasAccessTo(lesson_whatareyoucalled)).thenReturn(false)

        assertEquals(listOf(lesson_hello), mm.availableLessons(u))
    }

    @Test
    fun canGetLockedLessons() {
        val mm = ModuleMap(lesson_hello, lesson_whatareyoucalled)
        mm.dependency(lesson_hello, lesson_whatareyoucalled)
        val u = spy(User())
        `when`(u.hasAccessTo(lesson_hello)).thenReturn(true)
        `when`(u.hasAccessTo(lesson_whatareyoucalled)).thenReturn(false)

        assertEquals(listOf(lesson_whatareyoucalled), mm.visibleButLockedLessons(u))
    }
}