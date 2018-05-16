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
        override fun completeStage(qs: List<Question>, s: Screen, translationOverlay: TranslationOverlay, multipleChoiceOverlay: MultipleChoiceOverlay): Triple<Double, Int, Int> {
            assertTrue(stageMarker < 6)
            if (qs.isEmpty()) {
                return Triple(0.0, 0, 0)
            }
            val firstQ = qs.first()
            when (firstQ) {
                is MultipleChoiceQuestion -> {
                    if (wordMemo.isEmpty()) {
                        assertEquals(0, stageMarker)
                    } else {
                        assertTrue(stageMarker == 0 || stageMarker == 1)
                    }
                    stageMarker++
                }
                is TranslationQuestion -> {
                    if (alphabet.english.contains(firstQ.given.first())) { // english -> georgian
                        if (wordMemo.isEmpty()) {
                            assertEquals(1, stageMarker)
                        } else {
                            assertTrue(stageMarker == 2 || stageMarker == 3)
                        }
                        stageMarker++
                    } else if (alphabet.georgian.contains(firstQ.given.first())) { // georgian -> english
                        if (wordMemo.isEmpty()) {
                            assertEquals(2, stageMarker)
                        } else {
                            assertTrue(stageMarker == 4 || stageMarker == 5)
                        }
                        stageMarker++
                    } else {
                        assertTrue(false) // ...
                    }
                }
            }
            return Triple(10.0, 5, 0)
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
        assertEquals(3, memoLesson.stageMarker)
        // verify that a MCQ was made for each
        inOrder.verify(spyProductions).alphabetSound('a', "ant", 'ა', Triple('ს', 'მ', 'ე'))
        inOrder.verify(spyProductions).alphabetSound('b', "bee", 'ბ', Triple('გ', 'ფ', 'ა'))
        inOrder.verify(spyProductions).alphabetSound('g', "girl", 'გ', Triple('მ', 'შ', 'კ'))
        // verify that a TQ was made for each
        inOrder.verify(spyProductions).englishToGeorgian(t1)
        inOrder.verify(spyProductions).englishToGeorgian(t2)
        inOrder.verify(spyProductions).englishToGeorgian(t3)
        // verify that a RTQ was made for each
        inOrder.verify(spyProductions).georgianToEnglish(t1)
        inOrder.verify(spyProductions).georgianToEnglish(t2)
        inOrder.verify(spyProductions).georgianToEnglish(t3)
        // verify post completion actions
        inOrder.verify(mockScreen).closeInput()
        inOrder.verify(mockScreen).showPostLessonInfo(eq(100.0), eq(30.0), Matchers.anyString())
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
        val alphabetMemo = listOf(t1, t2, t3)
        val wordMemo = listOf(t4)

        val memoLesson = MockMemoLesson(spyProductions, alphabetMemo, wordMemo)

        memoLesson.complete(mockScreen, spyTranslationOverlay, spyMultipleChoiceOverlay)

        // verify that completeStage was called thrice
        assertEquals(6, memoLesson.stageMarker)
        // verify that a MCQ was made for each
        inOrder.verify(spyProductions).alphabetSound('a', "ant", 'ა', Triple('ს', 'მ', 'ე'))
        inOrder.verify(spyProductions).alphabetSound('b', "bee", 'ბ', Triple('გ', 'ფ', 'ა'))
        inOrder.verify(spyProductions).alphabetSound('g', "girl", 'გ', Triple('მ', 'შ', 'კ'))
        inOrder.verify(spyProductions).englishToGeorgianMultipleChoice("hello", "გამარჯობა", Triple("a", "b", "c"))
        // verify that a TQ was made for each
        inOrder.verify(spyProductions).englishToGeorgian(t1)
        inOrder.verify(spyProductions).englishToGeorgian(t2)
        inOrder.verify(spyProductions).englishToGeorgian(t3)
        inOrder.verify(spyProductions).englishToGeorgian(t4)
        // verify that a RTQ was made for each
        inOrder.verify(spyProductions).georgianToEnglish(t1)
        inOrder.verify(spyProductions).georgianToEnglish(t2)
        inOrder.verify(spyProductions).georgianToEnglish(t3)
        inOrder.verify(spyProductions).georgianToEnglish(t4)
        // verify post completion actions
        inOrder.verify(mockScreen).closeInput()
        inOrder.verify(mockScreen).showPostLessonInfo(eq(100.0), eq(60.0), Matchers.anyString())
        inOrder.verify(mockScreen).print()
        inOrder.verify(mockScreen).clear()
    }
}