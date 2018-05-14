/*Created on 08/05/18. */
import junit.framework.TestCase.assertEquals
import logic.Module
import logic.Question
import logic.Questions
import org.junit.Test
import org.mockito.Mockito.mock

class ModuleTest {
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

    @Test
    fun canAddQuestionsToModule() {
        val m = Module("hello")
        val qs = Questions(listOf(mock(Question::class.java)))

        m.addQuestions(qs)

        assertEquals(1, m.questions.count())
    }
}