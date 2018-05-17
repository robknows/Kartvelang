/*Created on 16/05/18. */
package logic.lesson

import logic.io.Screen
import logic.language.Letter
import logic.language.Productions
import logic.language.Translation
import logic.language.concat
import logic.overlay.MultipleChoiceOverlay
import logic.overlay.TranslationOverlay

open class WordsToPhrasesLesson(val p: Productions, val letters: List<Letter>, val words: List<Translation>, val phraseProductions: List<Pair<(Translation) -> Translation, List<Translation>>>) : Lesson {
    override fun countQuestions(): Int {
        return letters.size + 3 * (words.size + phraseProductions.size)
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
        val alphabetMultipleChoiceQuestions = letters.map({ l ->
            p.alphabetSound(l, similarLetters(l.kar))
        })
        val (aRuntime, aAnswered, aMistakes) = completeStage(alphabetMultipleChoiceQuestions, s, translationOverlay, multipleChoiceOverlay)

        val wordsMultipleChoiceQuestions = words.map({ t ->
            p.englishToGeorgianMultipleChoice(t.english, t.georgian, randomShortWords())
        })
        val (wRuntime, wAnswered, wMistakes) = completeStage(wordsMultipleChoiceQuestions, s, translationOverlay, multipleChoiceOverlay)

        val phraseMultipleChoiceQuestions = phraseProductions.map({ (production, args) ->
            if (args.isEmpty()) {
                val (english, georgian) = production(Translation("", ""))
                p.englishToGeorgianMultipleChoice(english, georgian, randomShortWords())
            } else {
                val (english, georgian) = production(args[0])
                p.englishToGeorgianMultipleChoice(english, georgian, randomShortWords())
            }
        })
        val (pRuntime, rAnswered, pMistakes) = completeStage(phraseMultipleChoiceQuestions, s, translationOverlay, multipleChoiceOverlay)

        return QuestionsResults(aRuntime + wRuntime + pRuntime, aAnswered + wAnswered + rAnswered, aMistakes + wMistakes + pMistakes)
    }

    fun completeEnglishToGeorgianStage(s: Screen, translationOverlay: TranslationOverlay, multipleChoiceOverlay: MultipleChoiceOverlay): QuestionsResults {
        val englishToGeorgianQuestions = concat(
                words.map(p::englishToGeorgian),
                phraseProductions.map({ (production, args) ->
                    if (args.isEmpty()) {
                        p.englishToGeorgian(production(Translation("", "")))
                    } else {
                        p.englishToGeorgian(production(args[1]))
                    }
                }))
        return completeStage(englishToGeorgianQuestions, s, translationOverlay, multipleChoiceOverlay)
    }

    fun completeGeorgianToEnglishStage(s: Screen, translationOverlay: TranslationOverlay, multipleChoiceOverlay: MultipleChoiceOverlay): QuestionsResults {
        val georgianToEnglishQuestions = concat(
                words.map(p::georgianToEnglish),
                phraseProductions.map({ (production, args) ->
                    if (args.isEmpty()) {
                        p.georgianToEnglish(production(Translation("", "")))
                    } else {
                        p.georgianToEnglish(production(args[1]))
                    }
                }))
        return completeStage(georgianToEnglishQuestions, s, translationOverlay, multipleChoiceOverlay)
    }
}
