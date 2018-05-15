package logic

open class MemoLesson(val p: Productions, val memo: List<Translation>) {
    fun complete(s: Screen, translationOverlay: TranslationOverlay, multipleChoiceOverlay: MultipleChoiceOverlay): Lesson.LessonResults {
        val multipleChoiceQs = memo.map({ t ->
            val eng = t.english.first()
            val kar = t.georgian.first()
            p.alphabetSound(eng, inWord(eng), kar, similarLetters(kar))
        })
        val (mcRuntime, mcAnswered, mcMistakes) = completeStage(multipleChoiceQs, s, translationOverlay, multipleChoiceOverlay)

        val englishToGeorgianQs = memo.map(p::englishToGeorgian)
        val (etgRuntime, etgAnswered, etgMistakes) = completeStage(englishToGeorgianQs, s, translationOverlay, multipleChoiceOverlay)

        val georgianToEnglishQs = memo.map(p::georgianToEnglish)
        val (gteRuntime, gteAnswered, gteMistakes) = completeStage(georgianToEnglishQs, s, translationOverlay, multipleChoiceOverlay)

        s.closeInput()

        val totalAnswered = mcAnswered + etgAnswered + gteAnswered
        val totalMistakes = mcMistakes + etgMistakes + gteMistakes

        val results = Lesson.LessonResults(
                100 * (totalAnswered - totalMistakes).toDouble() / totalAnswered,
                mcRuntime + etgRuntime + gteRuntime)

        s.showPostLessonInfo(results.accuracyPc, results.timeSeconds, randomHint())
        s.print()
        s.clear()
        return results
    }

    open fun completeStage(qs: List<Question>, s: Screen, translationOverlay: TranslationOverlay, multipleChoiceOverlay: MultipleChoiceOverlay): Triple<Double, Int, Int> {
        return Questions(qs).run(s, translationOverlay, multipleChoiceOverlay)
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