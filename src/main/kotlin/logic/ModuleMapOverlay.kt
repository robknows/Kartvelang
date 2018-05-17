package logic

import logic.lesson.Lesson

class ModuleMapOverlay {
    var availableLessons: List<Lesson> = listOf()

    override fun toString(): String {
        return "New\nFading\nDone" + availableLessons.map({ l -> "\n\t${l.name}" }).concat()
    }

    fun showAvailableLessons(mm: ModuleMap, u: User) {
        availableLessons = mm.availableLessons(u)
    }
}

fun List<String>.concat(): String {
    return this.reduce({ acc, s -> acc + s })
}
