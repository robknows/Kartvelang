/*Created on 15/05/18. */
import logic.*
import org.junit.Test
import org.mockito.Matchers
import org.mockito.Mockito
import org.mockito.Mockito.*

class MemoLessonTest {
    val mockScreen = Mockito.mock(Screen::class.java)
    val spyTranslationOverlay = spy(TranslationOverlay())
    val spyMultipleChoiceOverlay = spy(MultipleChoiceOverlay())

    // Mockito wasn't working
    class MockMemoLesson(p: Productions, memo: List<Translation>) : MemoLesson(p, memo) {
        override fun completeStage(qs: List<Question>, s: Screen, translationOverlay: TranslationOverlay, multipleChoiceOverlay: MultipleChoiceOverlay): Triple<Double, Int, Int> {
            return Triple(10.0, 5, 0)
        }
    }

    @Test
    fun memoLessonRunsStagesInOrder() {
        val spyProductions = spy(Productions())
        val inOrder = inOrder(spyProductions, mockScreen)
        val t1 = Translation("a", "ა")
        val t2 = Translation("b", "ბ")
        val t3 = Translation("g", "გ")
        val memo = listOf(t1, t2, t3)

        val memoLesson = MockMemoLesson(spyProductions, memo)

        memoLesson.complete(mockScreen, spyTranslationOverlay, spyMultipleChoiceOverlay)

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
}