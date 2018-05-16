package logic

open class MemoLesson(val p: Productions, val alphabetMemo: List<Translation>, val wordMemo: List<Translation>) {
    fun complete(s: Screen, translationOverlay: TranslationOverlay, multipleChoiceOverlay: MultipleChoiceOverlay): Lesson.LessonResults {
        val (mcRuntime, mcAnswered, mcMistakes) = completeMultipleChoiceStage(s, translationOverlay, multipleChoiceOverlay)
        val (etgRuntime, etgAnswered, etgMistakes) = completeEnglishToGeorgianStage(s, translationOverlay, multipleChoiceOverlay)
        val (gteRuntime, gteAnswered, gteMistakes) = completeGeorgianToEnglishStage(s, translationOverlay, multipleChoiceOverlay)

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

    fun completeMultipleChoiceStage(s: Screen, translationOverlay: TranslationOverlay, multipleChoiceOverlay: MultipleChoiceOverlay): Triple<Double, Int, Int> {
        val alphabetMultipleChoiceQs = alphabetMemo.map({ t ->
            val eng = t.english.first()
            val kar = t.georgian.first()
            p.alphabetSound(eng, inWord(eng), kar, similarLetters(kar))
        })
        val (aMcRuntime, aMcAnswered, aMcMistakes) = completeStage(alphabetMultipleChoiceQs, s, translationOverlay, multipleChoiceOverlay)

        val wordMultipleChoiceQs = wordMemo.map({ t -> p.englishToGeorgianMultipleChoiceTranslation(t.english, t.georgian, Triple("a", "b", "c")) })
        val (wMcRuntime, wMcAnswered, wMcMistakes) = completeStage(wordMultipleChoiceQs, s, translationOverlay, multipleChoiceOverlay)

        return Triple(aMcRuntime + wMcRuntime, aMcAnswered + wMcAnswered, aMcMistakes + wMcMistakes)
    }

    fun completeEnglishToGeorgianStage(s: Screen, translationOverlay: TranslationOverlay, multipleChoiceOverlay: MultipleChoiceOverlay): Triple<Double, Int, Int> {
        val alphabetEnglishToGeorgianQs = alphabetMemo.map(p::englishToGeorgian)
        val (aEtgRuntime, aEtgAnswered, aEtgMistakes) = completeStage(alphabetEnglishToGeorgianQs, s, translationOverlay, multipleChoiceOverlay)

        val wordEnglishToGeorgianQs = wordMemo.map(p::englishToGeorgian)
        val (wEtgRuntime, wEtgAnswered, wEtgMistakes) = completeStage(wordEnglishToGeorgianQs, s, translationOverlay, multipleChoiceOverlay)

        return Triple(aEtgRuntime + wEtgRuntime, aEtgAnswered + wEtgAnswered, aEtgMistakes + wEtgMistakes)
    }

    fun completeGeorgianToEnglishStage(s: Screen, translationOverlay: TranslationOverlay, multipleChoiceOverlay: MultipleChoiceOverlay): Triple<Double, Int, Int> {
        val alphabetGeorgianToEnglishQs = alphabetMemo.map(p::georgianToEnglish)
        val (aGteRuntime, aGteAnswered, aGteMistakes) = completeStage(alphabetGeorgianToEnglishQs, s, translationOverlay, multipleChoiceOverlay)

        val wordGeorgianToEnglishQs = wordMemo.map(p::georgianToEnglish)
        val (wGteRuntime, wGteAnswered, wGteMistakes) = completeStage(wordGeorgianToEnglishQs, s, translationOverlay, multipleChoiceOverlay)

        return Triple(aGteRuntime + wGteRuntime, aGteAnswered + wGteAnswered, aGteMistakes + wGteMistakes)
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