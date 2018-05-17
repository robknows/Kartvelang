/*Created on 17/05/18. */
import course.lesson_hello
import course.lesson_whatareyoucalled
import junit.framework.TestCase.assertEquals
import org.junit.Test

class ModuleMapTest {
    @Test
    fun canGetAvailableLessons() {
        val mm = ModuleMap(lesson_hello, lesson_whatareyoucalled)

        assertEquals(listOf(lesson_hello, lesson_whatareyoucalled), mm.lessons)
    }
}