/*Created on 15/05/18. */
package logic.lesson

import logic.io.Screen
import logic.language.Letter
import logic.language.Productions
import logic.language.Translation
import logic.overlay.MultipleChoiceOverlay
import logic.overlay.TranslationOverlay

open class MemoLesson(override val name: String, val p: Productions, val alphabetMemo: List<Letter>, val wordMemo: List<Translation>, override var dependencies: List<Lesson> = listOf()) : Lesson {
    override fun countQuestions(): Int {
        return alphabetMemo.size + 3 * wordMemo.size
    }

    override fun complete(s: Screen, translationOverlay: TranslationOverlay, multipleChoiceOverlay: MultipleChoiceOverlay): LessonResults {
        val (mcqRuntime, mcqAnswered, mcqMistakes) = completeMultipleChoiceStage(s, translationOverlay, multipleChoiceOverlay)
        val (etgRuntime, etgAnswered, etgMistakes) = completeEnglishToGeorgianStage(s, translationOverlay, multipleChoiceOverlay)
        val (gteRuntime, gteAnswered, gteMistakes) = completeGeorgianToEnglishStage(s, translationOverlay, multipleChoiceOverlay)

        s.closeInput()

        val totalAnswered = mcqAnswered + etgAnswered + gteAnswered
        val totalMistakes = mcqMistakes + etgMistakes + gteMistakes

        val results = LessonResults(
                100 * (totalAnswered - totalMistakes).toDouble() / totalAnswered,
                mcqRuntime + etgRuntime + gteRuntime)

        s.showPostLessonInfo(results.accuracyPc, results.timeSeconds, randomHint())
        s.print()
        s.clear()
        return results
    }

    fun completeMultipleChoiceStage(s: Screen, translationOverlay: TranslationOverlay, multipleChoiceOverlay: MultipleChoiceOverlay): QuestionsResults {
        val alphabetMultipleChoiceQs = alphabetMemo.map({ l ->
            p.alphabetSound(l, similarLetters(l.kar))
        })
        val (aMcRuntime, aMcAnswered, aMcMistakes) = completeStage(alphabetMultipleChoiceQs, s, translationOverlay, multipleChoiceOverlay)

        val wordMultipleChoiceQs = wordMemo.map({ t -> p.englishToGeorgianMultipleChoice(t.english, t.georgian, randomShortWords()) })
        val (wMcRuntime, wMcAnswered, wMcMistakes) = completeStage(wordMultipleChoiceQs, s, translationOverlay, multipleChoiceOverlay)

        return QuestionsResults(aMcRuntime + wMcRuntime, aMcAnswered + wMcAnswered, aMcMistakes + wMcMistakes)
    }

    fun completeEnglishToGeorgianStage(s: Screen, translationOverlay: TranslationOverlay, multipleChoiceOverlay: MultipleChoiceOverlay): QuestionsResults {
        val englishToGeorgianQs = wordMemo.map(p::englishToGeorgian)
        return completeStage(englishToGeorgianQs, s, translationOverlay, multipleChoiceOverlay)
    }

    fun completeGeorgianToEnglishStage(s: Screen, translationOverlay: TranslationOverlay, multipleChoiceOverlay: MultipleChoiceOverlay): QuestionsResults {
        val georgianToEnglishQs = wordMemo.map(p::georgianToEnglish)
        return completeStage(georgianToEnglishQs, s, translationOverlay, multipleChoiceOverlay)
    }
}

fun randomShortWords(): Triple<String, String, String> {
    return Triple("ხე", "ჩაი", "ათი")
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
        'შ' -> Triple('მ', 'ნ', 'წ')
        'ქ' -> Triple('ე', 'მ', 'გ')
        'ვ' -> Triple('კ', 'პ', 'ჰ')
        'ი' -> Triple('ა', 'ე', 'ო')
        'ე' -> Triple('ა', 'უ', 'ო')
        'ნ' -> Triple('მ', 'წ', 'ა')
        'თ' -> Triple('დ', 'ფ', 'ო')
        else -> {
            Triple('.', '.', '.')
        }
    }
}