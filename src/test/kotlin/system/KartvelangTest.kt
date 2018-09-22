/*Created on 22/09/18. */
package system

import logic.Kartvelang
import logic.User
import logic.io.ColourPrinter
import logic.io.Screen
import logic.lesson.Lesson
import logic.lesson.Questions
import logic.lesson.QuickLesson
import logic.question.MultipleChoiceChoice
import logic.question.MultipleChoiceQuestion
import logic.question.TranslationQuestion
import org.junit.Test
import org.mockito.Mockito.mock
import unit.fp
import java.io.BufferedReader
import java.io.StringReader
import java.nio.file.Files
import java.nio.file.Paths

class KartvelangTest {
    val q1 = TranslationQuestion("Type \"abc\"", "abc")
    val q2 = TranslationQuestion("Type \"doremi\"", "doremi")
    val q3 = TranslationQuestion("Type \"onetwothree\"", "onetwothree")
    val q4 = MultipleChoiceQuestion("is \"m\"", "m", Triple("a", "b", "c"), MultipleChoiceChoice.B)
    val q5 = MultipleChoiceQuestion("is \"z\"", "z", Triple("a", "b", "c"), MultipleChoiceChoice.A)
    val q6 = MultipleChoiceQuestion("is \"t\"", "t", Triple("a", "b", "c"), MultipleChoiceChoice.D)
    val mockPrinter = mock(ColourPrinter::class.java)

    /*
    In this test
        a user starts the app,
        picks a lesson,
        completes the lesson,
        has their profile saved
     */
    @Test(timeout = 3000)
    fun chooseAndCompleteLesson() {
        val input = BufferedReader(StringReader("Lesson Du Test\n\ndoremi\n\nb\n\nd\n\n\n"))
        val screen = Screen(mockPrinter, input)
        val lesson1 = QuickLesson("Test Lesson", Questions(listOf(q1, q3, q5)))
        val lesson2 = QuickLesson("Lesson Du Test", Questions(listOf(q2, q4, q6)))
        val user = object : User() {
            override fun currentLessons(): List<Lesson> {
                return listOf(lesson1, lesson2)
            }
        }
        user.profileFile = fp("src/test/resources/testuser.json")
        val app = Kartvelang(screen, user)

        app.run()

        Files.delete(Paths.get(fp("src/test/resources/testuser.json")))
    }
}