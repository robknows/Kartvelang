package logic.overlay

import logic.io.ColourPrinter
import logic.lesson.Lesson

class LessonMapOverlay(val lessons: List<Lesson>) : Overlay {
    override fun printWith(printer: ColourPrinter) {
        printer.printlnWhite("Choose a lesson")
        for (lesson in lessons) {
            printer.printlnWhite("- " + lesson.name)
        }
    }

    override fun clear() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun maxLineLength(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
