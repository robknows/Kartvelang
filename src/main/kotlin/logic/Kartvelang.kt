/*Created on 22/09/18. */
package logic

import logic.io.Key
import logic.io.Screen
import logic.overlay.MultipleChoiceOverlay
import logic.overlay.TranslationOverlay

class Kartvelang(val screen: Screen, private val user: User) {
    fun run() {
        screen.printCoverScreen()
        val lessonMap = LessonMap(user, screen)
        val selectedLesson = lessonMap.navigate()
        screen.promptForKeyPress("Now starting ${selectedLesson.name}. Press enter to continue", Key.ENTER)
        user.complete(selectedLesson, screen, TranslationOverlay(), MultipleChoiceOverlay())
        user.saveProfile()
    }
}