/*Created on 22/09/18. */
package logic

import logic.io.Key
import logic.io.Screen
import logic.lesson.Lesson
import logic.overlay.LessonMapOverlay
import logic.overlay.MultipleChoiceOverlay
import logic.overlay.TranslationOverlay

class Kartvelang(val screen: Screen, private val user: User) {
    fun run() {
        screen.printCoverScreen()
        val selectedLesson = navigateLessonMap()
        startLesson(user, selectedLesson)
    }

    fun navigateLessonMap(): Lesson {
        val lessons = user.currentLessons()
        val lessonMapOverlay = LessonMapOverlay(lessons)
        screen.overlay = lessonMapOverlay
        screen.print()
        return awaitValidLessonChoice(lessons)
    }

    private fun startLesson(user: User, lesson: Lesson) {
        screen.promptForKeyPress("Now starting ${lesson.name}. Press enter to continue", Key.ENTER)
        user.complete(lesson, screen, TranslationOverlay(), MultipleChoiceOverlay())
        user.saveProfile("/home/rob/Documents/language/Kartvelang/kartvelang_user.json")
    }

    private fun awaitValidLessonChoice(shownLessons: List<Lesson>): Lesson {
        var input = screen.awaitLine()
        while (!isValidLessonChoice(shownLessons, input)) {
            input = screen.awaitPromptedLine("Choose a lesson from the list")
        }
        val selectedLesson = shownLessons.first({ lesson -> lesson.name == input })
        return selectedLesson
    }

    fun isValidLessonChoice(shownLessons: List<Lesson>, input: String): Boolean {
        return shownLessons.map(Lesson::name).contains(input)
    }
}