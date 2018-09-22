package unit

/*Created on 15/05/18. */
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import logic.io.Screen
import logic.language.*
import logic.lesson.MemoLesson
import logic.lesson.QuestionsResults
import logic.overlay.MultipleChoiceOverlay
import logic.overlay.TranslationOverlay
import logic.question.MultipleChoiceQuestion
import logic.question.Question
import logic.question.TranslationQuestion
import org.junit.Test
import org.mockito.Matchers
import org.mockito.Mockito
import org.mockito.Mockito.*

class MemoLessonTest {
    val mockScreen = Mockito.mock(Screen::class.java)
    val spyTranslationOverlay = spy(TranslationOverlay())
    val spyMultipleChoiceOverlay = spy(MultipleChoiceOverlay())

    // Mockito wasn't working so I got the axe out
    class MockMemoLesson(p: Productions, alphabetMemo: List<Letter>, wordMemo: List<Translation>) : MemoLesson("", p, alphabetMemo, wordMemo) {
        var stageMarker: Int = 0
        val noWordsP: Int = if (wordMemo.isNotEmpty()) 1 else 0
        override fun completeStage(qs: List<Question>, s: Screen, translationOverlay: TranslationOverlay, multipleChoiceOverlay: MultipleChoiceOverlay): QuestionsResults {
            if (qs.isEmpty()) {
                return QuestionsResults(0.0, 0, 0)
            } else {
                val firstQ = qs.first()
                when (firstQ) {
                    is MultipleChoiceQuestion -> { // multiple choice
                        assertTrue(stageMarker < 1 + noWordsP)
                        stageMarker++
                    }
                    is TranslationQuestion -> {
                        assertTrue(stageMarker > noWordsP)
                        val letter = firstQ.given.first()
                        when {
                            alphabet.english.contains(letter) -> { // english -> georgian
                                assertTrue(stageMarker < 2 + noWordsP)
                                stageMarker++
                            }
                            alphabet.georgian.contains(letter) -> { // georgian -> english
                                assertTrue(stageMarker > 1 + noWordsP)
                                assertTrue(stageMarker < 3 + noWordsP)
                                stageMarker++
                            }
                            else -> {
                                assertTrue(false)
                            }
                        }
                    }
                }
                return QuestionsResults(10.0, 5, 0)
            }
        }
    }

    @Test
    fun runsStagesInOrder() {
        val spyProductions = spy(Productions())
        val inOrder = inOrder(spyProductions, mockScreen)
        val memo = listOf(letter_a, letter_b, letter_g)

        val memoLesson = MockMemoLesson(spyProductions, memo, listOf())

        memoLesson.complete(mockScreen, spyTranslationOverlay, spyMultipleChoiceOverlay)

        // verify that completeStage was called only once - no translation questions
        assertEquals(1, memoLesson.stageMarker)
        // verify that a MCQ was made for each
        inOrder.verify(spyProductions).alphabetSound(letter_a, Triple('ს', 'მ', 'ე'))
        inOrder.verify(spyProductions).alphabetSound(letter_b, Triple('გ', 'ფ', 'ა'))
        inOrder.verify(spyProductions).alphabetSound(letter_g, Triple('მ', 'შ', 'კ'))
        // verify post completion actions
        inOrder.verify(mockScreen).closeInput()
        inOrder.verify(mockScreen).showPostLessonInfo(eq(100.0), eq(10.0), Matchers.anyString())
        inOrder.verify(mockScreen).print()
        inOrder.verify(mockScreen).clear()
    }

    @Test
    fun alphabetQuestionsComeFirst() {
        val spyProductions = spy(Productions())
        val inOrder = inOrder(spyProductions, mockScreen)
        val t4 = Translation("hello", "გამარჯობა")
        val t5 = Translation("thanks", "გმადლობ")
        val alphabetMemo = listOf(letter_a, letter_b, letter_g)
        val wordMemo = listOf(t4, t5)

        val memoLesson = MockMemoLesson(spyProductions, alphabetMemo, wordMemo)

        memoLesson.complete(mockScreen, spyTranslationOverlay, spyMultipleChoiceOverlay)

        // verify that completeStage was called thrice
        assertEquals(4, memoLesson.stageMarker)
        // verify that a MCQ was made for each
        inOrder.verify(spyProductions).alphabetSound(letter_a, Triple('ს', 'მ', 'ე'))
        inOrder.verify(spyProductions).alphabetSound(letter_b, Triple('გ', 'ფ', 'ა'))
        inOrder.verify(spyProductions).alphabetSound(letter_g, Triple('მ', 'შ', 'კ'))
        inOrder.verify(spyProductions).englishToGeorgianMultipleChoice("hello", "გამარჯობა", Triple("ხე", "ჩაი", "ათი"))
        // verify that a TQ was made for each word
        inOrder.verify(spyProductions).englishToGeorgian(t4)
        inOrder.verify(spyProductions).englishToGeorgian(t5)
        // verify that a RTQ was made for each word
        inOrder.verify(spyProductions).georgianToEnglish(t4)
        inOrder.verify(spyProductions).georgianToEnglish(t5)
        // verify post completion actions
        inOrder.verify(mockScreen).closeInput()
        inOrder.verify(mockScreen).showPostLessonInfo(eq(100.0), eq(40.0), Matchers.anyString())
        inOrder.verify(mockScreen).print()
        inOrder.verify(mockScreen).clear()
    }

    @Test
    fun canCountQuestions() {
        assertEquals(9, MemoLesson("", Productions(),
                listOf(letter_g, letter_a, letter_m, letter_r, letter_j, letter_b),
                listOf(greeting_hello)).countQuestions())
    }
}