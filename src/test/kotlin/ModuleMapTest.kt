/*Created on 08/05/18. */
import junit.framework.TestCase.assertEquals
import org.junit.Test

class ModuleMapTest {
    @Test
    fun canGetName() {
        val m = Module("greetings")

        assertEquals(m.name, "greetings")
    }

    @Test
    fun canAddDependencies() {
        val m1 = Module("greetings")
        val m2 = Module("advanced greetings")

        m2.addDependency(m1)

        assertEquals(m2.dependencies, listOf(m1))
    }
}