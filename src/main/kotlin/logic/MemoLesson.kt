/*Created on 15/05/18. */
package logic

open class MemoLesson(val p: Productions, val alphabetMemo: List<Translation>, val wordMemo: List<Translation>) : Lesson {
    override fun complete(s: Screen, translationOverlay: TranslationOverlay, multipleChoiceOverlay: MultipleChoiceOverlay): LessonResults {
        val (mcRuntime, mcAnswered, mcMistakes) = completeMultipleChoiceStage(s, translationOverlay, multipleChoiceOverlay)
        val (etgRuntime, etgAnswered, etgMistakes) = completeEnglishToGeorgianStage(s, translationOverlay, multipleChoiceOverlay)
        val (gteRuntime, gteAnswered, gteMistakes) = completeGeorgianToEnglishStage(s, translationOverlay, multipleChoiceOverlay)

        s.closeInput()

        val totalAnswered = mcAnswered + etgAnswered + gteAnswered
        val totalMistakes = mcMistakes + etgMistakes + gteMistakes

        val results = LessonResults(
                100 * (totalAnswered - totalMistakes).toDouble() / totalAnswered,
                mcRuntime + etgRuntime + gteRuntime)

        s.showPostLessonInfo(results.accuracyPc, results.timeSeconds, randomHint())
        s.print()
        s.clear()
        return results
    }

    fun completeMultipleChoiceStage(s: Screen, translationOverlay: TranslationOverlay, multipleChoiceOverlay: MultipleChoiceOverlay): Triple<Double, Int, Int> {
        val alphabetMultipleChoiceQs = alphabetMemo.map({ t ->
            val eng = t.english.first()
            val kar = t.georgian.first()
            p.alphabetSound(eng, inWord(eng), kar, similarLetters(kar))
        })
        val (aMcRuntime, aMcAnswered, aMcMistakes) = completeStage(alphabetMultipleChoiceQs, s, translationOverlay, multipleChoiceOverlay)

        val wordMultipleChoiceQs = wordMemo.map({ t -> p.englishToGeorgianMultipleChoice(t.english, t.georgian, Triple("a", "b", "c")) })
        val (wMcRuntime, wMcAnswered, wMcMistakes) = completeStage(wordMultipleChoiceQs, s, translationOverlay, multipleChoiceOverlay)

        return Triple(aMcRuntime + wMcRuntime, aMcAnswered + wMcAnswered, aMcMistakes + wMcMistakes)
    }

    fun completeEnglishToGeorgianStage(s: Screen, translationOverlay: TranslationOverlay, multipleChoiceOverlay: MultipleChoiceOverlay): Triple<Double, Int, Int> {
        val englishToGeorgianQs = wordMemo.map(p::englishToGeorgian)
        return completeStage(englishToGeorgianQs, s, translationOverlay, multipleChoiceOverlay)

    }

    fun completeGeorgianToEnglishStage(s: Screen, translationOverlay: TranslationOverlay, multipleChoiceOverlay: MultipleChoiceOverlay): Triple<Double, Int, Int> {
        val georgianToEnglishQs = wordMemo.map(p::georgianToEnglish)
        return completeStage(georgianToEnglishQs, s, translationOverlay, multipleChoiceOverlay)
    }

    // Returns a triple of (Runtime, #answered, #mistakes)
    open fun completeStage(qs: List<Question>, s: Screen, translationOverlay: TranslationOverlay, multipleChoiceOverlay: MultipleChoiceOverlay): Triple<Double, Int, Int> {
        if (qs.isEmpty()) {
            return Triple(0.0, 0, 0)
        } else {
            return Questions(qs).run(s, translationOverlay, multipleChoiceOverlay)
        }
    }
}

fun inWord(eng: Char): String {
    return when (eng) {
        'a' -> "ant"
        'b' -> "bee"
        'g' -> "girl"
        'm' -> "morning"
        'r' -> "rock"
        'j' -> "major"
        'o' -> "court"
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
        'მ' -> Triple('წ', 'შ', 'ო')
        'რ' -> Triple('ო', 'უ', 'დ')
        'ჯ' -> Triple('ჩ', 'ყ', 'ლ')
        'ო' -> Triple('რ', 'ე', 'თ')
        else -> {
            Triple('x', 'y', 'z')
        }
    }
}