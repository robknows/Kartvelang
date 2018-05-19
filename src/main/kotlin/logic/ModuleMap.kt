package logic

import logic.lesson.Lesson

class ModuleMap(vararg lessons: Lesson) {
    val lessons = lessons.toList()
    val dependencies: MutableMap<Lesson, MutableList<Lesson>> = HashMap()

    fun availableLessons(u: User): List<Lesson> {
        return lessons.filter(u::hasAccessTo)
    }

    fun visibleButLockedLessons(u: User): List<Lesson> {
        val availableLessons = availableLessons(u)
        return dependencies.filterValues({ ls -> availableLessons.any(ls::contains) }).keys.toList()
    }

    fun dependency(required_lesson: Lesson, dependent_lesson: Lesson) {
        if (dependencies.containsKey(dependent_lesson)) {
            dependencies[dependent_lesson]!!.add(required_lesson)
        } else {
            dependencies[dependent_lesson] = mutableListOf(required_lesson)
        }
    }
}
