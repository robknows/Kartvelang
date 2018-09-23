package logic.overlay

import logic.io.ColourPrinter
import logic.io.Screen
import logic.io.Text
import logic.lesson.Lesson
import logic.question.homogenise
import kotlin.math.max

class LessonMapOverlay(private val shownLessons: Set<Lesson>, private val screen: Screen) : Overlay {
    var lessonSelectionPromptLine = Text("")
    var mapLines: MutableList<Text> = mutableListOf()

    fun awaitLessonSelection(): Lesson {
        screen.overlay = this
        showMap()
        showLessonSelectionPrompt()
        screen.print()
        return awaitValidLessonChoice(shownLessons)
    }

    fun showLessonSelectionPrompt() {
        lessonSelectionPromptLine = Text("Choose a lesson")
    }

    fun showMap() {
        for (lesson in shownLessons) {
            mapLines.add(Text("- " + lesson.name))
        }
    }

    private fun awaitValidLessonChoice(shownLessons: Set<Lesson>): Lesson {
        var input = screen.awaitLine()
        while (!isValidLessonChoice(shownLessons, input)) {
            input = screen.awaitPromptedLine("Choose a lesson from the list")
        }
        return findIntendedLessonMatch(input, shownLessons)!!
    }

    private fun isValidLessonChoice(shownLessons: Set<Lesson>, input: String): Boolean {
        val match = findIntendedLessonMatch(input, shownLessons)
        return (match != null)
    }

    private fun findIntendedLessonMatch(needle: String, haystack: Set<Lesson>): Lesson? {
        return haystack.firstOrNull({ lesson -> homogenise(lesson.name) == homogenise(needle) })
    }

    override fun printWith(printer: ColourPrinter) {
        lessonSelectionPromptLine.printlnWith(printer)
        for (lessonOption in mapLines) {
            lessonOption.printlnWith(printer)
        }
    }

    override fun clear() {
        lessonSelectionPromptLine = Text("")
        mapLines = mutableListOf()
    }

    override fun maxLineLength(): Int {
        if (shownLessons.isEmpty()) {
            return "Choose a lesson".length
        } else {
            val maxLessonNameLength = shownLessons.map({ lesson -> lesson.name.length }).max()!!
            return max("Choose a lesson".length, ("- ".length + maxLessonNameLength))
        }
    }
}
