package logic.overlay

import logic.io.ColourPrinter
import logic.lesson.Lesson
import kotlin.math.max

class LessonMapOverlay(val lessons: List<Lesson>) : Overlay {
    override fun printWith(printer: ColourPrinter) {
        printer.printlnWhite("Choose a lesson")
        for (lesson in lessons) {
            printer.printlnWhite("- " + lesson.name)
        }
    }

    override fun clear() {
        // No state so doesn't need to do anything
    }

    override fun maxLineLength(): Int {
        if (lessons.isEmpty()) {
            return "Choose a lesson".length
        } else {
            val maxLessonNameLength = lessons.map({ lesson -> lesson.name.length }).max()!!
            return max("Choose a lesson".length, ("- ".length + maxLessonNameLength))
        }
    }
}
