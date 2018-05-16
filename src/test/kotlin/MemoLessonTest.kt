/*Created on 15/05/18. */
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import logic.*
import org.junit.Test
import org.mockito.Matchers
import org.mockito.Mockito
import org.mockito.Mockito.*

class MemoLessonTest {
    val mockScreen = Mockito.mock(Screen::class.java)
    val spyTranslationOverlay = spy(TranslationOverlay())
    val spyMultipleChoiceOverlay = spy(MultipleChoiceOverlay())

    // Mockito wasn't working so I got the axe out
    class MockMemoLesson(p: Productions, alphabetMemo: List<Translation>, wordMemo: List<Translation>) : MemoLesson(p, alphabetMemo, wordMemo) {
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
        val t1 = Translation("a", "ა")
        val t2 = Translation("b", "ბ")
        val t3 = Translation("g", "გ")
        val memo = listOf(t1, t2, t3)

        val memoLesson = MockMemoLesson(spyProductions, memo, listOf())

        memoLesson.complete(mockScreen, spyTranslationOverlay, spyMultipleChoiceOverlay)

        // verify that completeStage was called thrice
        assertEquals(1, memoLesson.stageMarker)
        // verify that a MCQ was made for each
        inOrder.verify(spyProductions).alphabetSound('a', "ant", 'ა', Triple('ს', 'მ', 'ე'))
        inOrder.verify(spyProductions).alphabetSound('b', "bee", 'ბ', Triple('გ', 'ფ', 'ა'))
        inOrder.verify(spyProductions).alphabetSound('g', "girl", 'გ', Triple('მ', 'შ', 'კ'))
        // verify that no TQs were made
        verify(spyProductions, never()).englishToGeorgian(t1)
        verify(spyProductions, never()).englishToGeorgian(t2)
        verify(spyProductions, never()).englishToGeorgian(t3)
        // verify that no RTQs was made
        verify(spyProductions, never()).georgianToEnglish(t1)
        verify(spyProductions, never()).georgianToEnglish(t2)
        verify(spyProductions, never()).georgianToEnglish(t3)
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
        val t1 = Translation("a", "ა")
        val t2 = Translation("b", "ბ")
        val t3 = Translation("g", "გ")
        val t4 = Translation("hello", "გამარჯობა")
        val t5 = Translation("thanks", "გმადლობ")
        val alphabetMemo = listOf(t1, t2, t3)
        val wordMemo = listOf(t4, t5)

        val memoLesson = MockMemoLesson(spyProductions, alphabetMemo, wordMemo)

        memoLesson.complete(mockScreen, spyTranslationOverlay, spyMultipleChoiceOverlay)

        // verify that completeStage was called thrice
        assertEquals(4, memoLesson.stageMarker)
        // verify that a MCQ was made for each
        inOrder.verify(spyProductions).alphabetSound('a', "ant", 'ა', Triple('ს', 'მ', 'ე'))
        inOrder.verify(spyProductions).alphabetSound('b', "bee", 'ბ', Triple('გ', 'ფ', 'ა'))
        inOrder.verify(spyProductions).alphabetSound('g', "girl", 'გ', Triple('მ', 'შ', 'კ'))
        inOrder.verify(spyProductions).englishToGeorgianMultipleChoice("hello", "გამარჯობა", Triple("შენ", "ჩაი", "ათი"))
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
}