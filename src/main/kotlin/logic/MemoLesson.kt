package logic

class MemoLesson(val p: Productions, val memo: List<Translation>) {
    fun complete() {
        val mcqs = memo.map({ t ->
            val eng = t.english.first()
            val kar = t.georgian.first()
            p.alphabetSound(eng, inWord(eng), kar, similarLetters(kar))
        })
        val tqs = memo.map(p::englishToGeorgian)
        val rtqs = memo.map(p::georgianToEnglish)
    }
}

fun inWord(eng: Char): String {
    return when (eng) {
        'a' -> "ant"
        'b' -> "bee"
        'g' -> "girl"
        else -> {
            ""
        }
    }
}

fun similarLetters(kar: Char): Triple<Char, Char, Char> {
    return when (kar) {
        'ა' -> Triple('ს', 'მ', 'ე')
        'ბ' -> Triple('გ', 'ფ', 'ა')
        'გ' -> Triple('მ', 'შ', 'კ')
        else -> {
            Triple('x', 'y', 'z')
        }
    }
}