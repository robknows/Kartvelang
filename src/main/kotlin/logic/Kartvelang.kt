/*Created on 22/09/18. */
package logic

import course.lesson_hello
import course.lesson_whatareyoucalled
import logic.io.Key
import logic.io.Screen
import logic.overlay.LessonMapOverlay
import logic.overlay.MultipleChoiceOverlay
import logic.overlay.TranslationOverlay

class Kartvelang(val screen: Screen, private val user: User) {
    private val allLessons = setOf(lesson_hello, lesson_whatareyoucalled)

    fun run() {
        screen.printCoverScreen()

        val shownLessons = user.availableLessons(allLessons)
        val lessonMapOverlay = LessonMapOverlay(shownLessons, screen)
        val selectedLesson = lessonMapOverlay.awaitLessonSelection()

        screen.promptForKeyPress("Now starting ${selectedLesson.name}. Press enter to continue", Key.ENTER)
        user.complete(selectedLesson, screen, TranslationOverlay(), MultipleChoiceOverlay())
        user.saveProfile()
    }
}