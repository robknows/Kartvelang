/*Created on 16/05/18. */
import junit.framework.TestCase.assertEquals
import logic.*
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.*

open class WordsToPhrasesLessonTest {
    val mockScreen = Mockito.mock(Screen::class.java)
    val spyTranslationOverlay = Mockito.spy(TranslationOverlay())
    val spyMultipleChoiceOverlay = Mockito.spy(MultipleChoiceOverlay())

    // Mockito wasn't working so I got the axe out
    class MockWtpLesson(p: Productions, letters: List<Translation>, words: List<Translation>, phraseProductions: List<Pair<(Translation) -> Translation, List<Translation>>>) : WordsToPhrasesLesson(p, letters, words, phraseProductions) {
        override fun completeStage(qs: List<Question>, s: Screen, translationOverlay: TranslationOverlay, multipleChoiceOverlay: MultipleChoiceOverlay): QuestionsResults {
            if (qs.isEmpty()) {
                return QuestionsResults(0.0, 0, 0)
            } else {
                return QuestionsResults(10.0, 5, 0)
            }
        }
    }

    @Test
    fun runsStagesInOrder() {
        val spyProductions = spy(Productions())
        val inOrder = inOrder(spyProductions)

        val wtpLesson = MockWtpLesson(
                spyProductions,
                listOf(alphabet_v),
                listOf(question_what, pronoun_1st_s_nom, pronoun_2nd_s_nom),
                listOf(
                        Pair({ _: Translation -> Translation("What is your name?", "შენ რა გქვია?") }, listOf()),
                        Pair({ name: Translation -> Translation("My name is ${name.english}", "მე ${name.georgian} მქვია") }, listOf(name_Keti, name_Tengo)))
        )

        assertEquals(LessonResults(100.0, 50.0), wtpLesson.complete(mockScreen, spyTranslationOverlay, spyMultipleChoiceOverlay))

        // verify alphabet MCQs
        inOrder.verify(spyProductions).alphabetSound("v", "voice", 'ვ', Triple('კ', 'პ', 'ჰ'))
        // verify word MCQs
        inOrder.verify(spyProductions).englishToGeorgianMultipleChoice(question_what.english, question_what.georgian, Triple("ხე", "ჩაი", "ათი"))
        inOrder.verify(spyProductions).englishToGeorgianMultipleChoice(pronoun_1st_s_nom.english, pronoun_1st_s_nom.georgian, Triple("ხე", "ჩაი", "ათი"))
        inOrder.verify(spyProductions).englishToGeorgianMultipleChoice(pronoun_2nd_s_nom.english, pronoun_2nd_s_nom.georgian, Triple("ხე", "ჩაი", "ათი"))
        // verify phrase MCQs
        inOrder.verify(spyProductions).englishToGeorgianMultipleChoice("What is your name?", "შენ რა გქვია?", Triple("ხე", "ჩაი", "ათი"))
        inOrder.verify(spyProductions).englishToGeorgianMultipleChoice("My name is Keti", "მე ქეთი მქვია", Triple("ხე", "ჩაი", "ათი"))
        // verify english to georgian word + phrase translations
        verify(spyProductions).englishToGeorgian(question_what)
        verify(spyProductions).englishToGeorgian(pronoun_1st_s_nom)
        verify(spyProductions).englishToGeorgian(pronoun_2nd_s_nom)
        verify(spyProductions).englishToGeorgian(Translation("What is your name?", "შენ რა გქვია?"))
        verify(spyProductions).englishToGeorgian(Translation("My name is Tengo", "მე თენგო მქვია"))
        // verify georgian to english word + phrase translations
        verify(spyProductions).georgianToEnglish(question_what)
        verify(spyProductions).georgianToEnglish(pronoun_1st_s_nom)
        verify(spyProductions).georgianToEnglish(pronoun_2nd_s_nom)
        verify(spyProductions).georgianToEnglish(Translation("What is your name?", "შენ რა გქვია?"))
        verify(spyProductions).georgianToEnglish(Translation("My name is Tengo", "მე თენგო მქვია"))
    }
}