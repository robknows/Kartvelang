package logic

import logic.lesson.Lesson

class ModuleMap(vararg lessons: Lesson) {
    val lessons = lessons.toList()

    fun availableLessons(u: User): List<Lesson> {
        return lessons.filter(u::hasAccessTo)
    }
}
