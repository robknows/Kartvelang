/*Created on 17/05/18. */
import course.lesson_hello
import course.lesson_whatareyoucalled
import junit.framework.TestCase.assertEquals
import logic.ModuleMap
import logic.ModuleMapOverlay
import logic.User
import org.junit.Test
import org.mockito.Mockito

class ModuleMapOverlayTest {
    val mmo = ModuleMapOverlay()

    @Test
    fun canShowAvailableLessons() {
        val mm = ModuleMap(lesson_hello, lesson_whatareyoucalled)
        val u = Mockito.spy(User())
        Mockito.`when`(u.hasAccessTo(lesson_hello)).thenReturn(true)
        Mockito.`when`(u.hasAccessTo(lesson_whatareyoucalled)).thenReturn(false)

        mmo.showAvailableLessons(mm, u)

        assertEquals("New\nFading\nDone\n\thello", mmo.toString())
    }
}