import logic.Productions
import logic.Translation

class MemoLesson(val p: Productions, val memo: List<Translation>) {
    fun complete() {
        val mcqs = memo.map({ t ->
            val eng = t.english.first()
            val kar = t.georgian.first()
            p.alphabetSound(eng, inWord(eng), kar, Triple('x', 'y', 'z'))
        })
        val tqs = memo.map(p::englishToGeorgian)
        val rtqs = memo.map(p::georgianToEnglish)
    }
}

fun inWord(englishLetter: Char): String {
    val c = englishLetter
    return when (c) {
        'a' -> "ant"
        'b' -> "bee"
        'g' -> "girl"
        else -> {
            ""
        }
    }
}