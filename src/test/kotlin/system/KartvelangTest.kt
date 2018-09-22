/*Created on 22/09/18. */
package system

import junit.framework.TestCase
import logic.Kartvelang
import logic.User
import logic.io.ColourPrinter
import logic.io.Key
import logic.io.Screen
import logic.lesson.Lesson
import logic.lesson.Questions
import logic.lesson.QuickLesson
import logic.question.MultipleChoiceChoice
import logic.question.MultipleChoiceQuestion
import logic.question.TranslationQuestion
import org.junit.Test
import org.mockito.ArgumentCaptor.forClass
import org.mockito.Matchers
import org.mockito.Mockito.*
import unit.fp
import java.io.BufferedReader
import java.io.File
import java.io.StringReader
import java.nio.file.Files
import java.nio.file.Paths

class KartvelangTest {
    val q1 = TranslationQuestion("Type \"abc\"", "abc")
    val q2 = spy(TranslationQuestion("Type \"doremi\"", "doremi"))
    val q3 = TranslationQuestion("Type \"onetwothree\"", "onetwothree")
    val q4 = spy(MultipleChoiceQuestion("is \"m\"", "m", Triple("a", "b", "c"), MultipleChoiceChoice.B))
    val q5 = MultipleChoiceQuestion("is \"z\"", "z", Triple("a", "b", "c"), MultipleChoiceChoice.A)
    val q6 = spy(MultipleChoiceQuestion("is \"t\"", "t", Triple("a", "b", "c"), MultipleChoiceChoice.D))
    val mockPrinter = mock(ColourPrinter::class.java)

    /*
    In this test
        a user starts the app,
        picks a lesson,
        completes the lesson (making no mistakes),
        has their profile saved to file
     */
    @Test(timeout = 3000)
    fun chooseAndCompleteLesson() {
        // Setup variables
        val input = BufferedReader(StringReader("Lesson Du Test\n\ndoremi\n\nb\n\nd\n\n\n"))
        val spyScreen = spy(Screen(mockPrinter, input))
        val lesson1 = QuickLesson("Test Lesson", Questions(listOf(q1, q3, q5)))
        val lesson2 = QuickLesson("Lesson Du Test", Questions(listOf(q2, q4, q6)))
        val user = object : User() {
            override fun currentLessons(): List<Lesson> {
                return listOf(lesson1, lesson2)
            }
        }
        user.profileFile = fp("src/test/resources/testuser.json")
        val app = Kartvelang(spyScreen, user)
        val inOrder = inOrder(spyScreen, mockPrinter, q2, q4, q6)

        // Go

        app.run()

        // Assertions

        inOrder.verify(spyScreen).printCoverScreen()
        // choosing a lesson from the LessonMap
        inOrder.verify(spyScreen).print()
        inOrder.verify(spyScreen).awaitLine()
        inOrder.verify(spyScreen).promptForKeyPress("Now starting Lesson Du Test. Press enter to continue", Key.ENTER)
        // completing q2
        inOrder.verify(spyScreen).print()
        inOrder.verify(spyScreen).awaitLine()
        inOrder.verify(q2).markAnswer("doremi")
        inOrder.verify(spyScreen).awaitKeyPress(Key.ENTER)
        inOrder.verify(spyScreen).clear()
        // completing q4
        inOrder.verify(spyScreen).print()
        inOrder.verify(spyScreen).awaitLine()
        inOrder.verify(q4).markAnswer("b")
        inOrder.verify(spyScreen).awaitKeyPress(Key.ENTER)
        inOrder.verify(spyScreen).clear()
        // completing q6
        inOrder.verify(spyScreen).print()
        inOrder.verify(spyScreen).awaitLine()
        inOrder.verify(q6).markAnswer("d")
        inOrder.verify(spyScreen).awaitKeyPress(Key.ENTER)
        inOrder.verify(spyScreen).clear()
        // showing the results for the now-completed lesson
        inOrder.verify(spyScreen).closeInput()
        val lessonTimeCaptor = forClass(Double::class.java)
        inOrder.verify(spyScreen).showPostLessonInfo(eq(100.0), lessonTimeCaptor.capture(), Matchers.anyString())
        inOrder.verify(spyScreen).print()
        inOrder.verify(spyScreen).clear()
        // saving user profile to file
        val bufferedReader = File(user.profileFile).inputStream().reader().buffered()
        val text = bufferedReader.readText()
        bufferedReader.close()
        Files.delete(Paths.get(user.profileFile))
        TestCase.assertEquals("{\"meanDailyAccuracy\":100,\"lastCompletion\":${user.lastCompletion},\"dailyLessonCompletions\":1,\"lessonTime\":${lessonTimeCaptor.value!!},\"totalLessonCompletions\":1}", text)
    }
}