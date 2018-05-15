/*Created on 15/05/18. */
import logic.Productions
import logic.Translation
import org.junit.Test
import org.mockito.Mockito.inOrder
import org.mockito.Mockito.spy

class MemoLessonTest {
    @Test
    fun memoLessonCreatesAllRequiredQuestions() {
        val spyProductions = spy(Productions())
        val inOrder = inOrder(spyProductions)
        val t1 = Translation("a", "ა")
        val t2 = Translation("b", "ბ")
        val t3 = Translation("g", "გ")
        val memo = listOf(t1, t2, t3)

        val lesson = MemoLesson(spyProductions, memo)

        lesson.complete()

        // verify that a MCQ was made for each
        inOrder.verify(spyProductions).alphabetSound('a', "ant", 'ა', Triple('x', 'y', 'z'))
        inOrder.verify(spyProductions).alphabetSound('b', "bee", 'ბ', Triple('x', 'y', 'z'))
        inOrder.verify(spyProductions).alphabetSound('g', "girl", 'გ', Triple('x', 'y', 'z'))
        // verify that a TQ was made for each
        inOrder.verify(spyProductions).englishToGeorgian(t1)
        inOrder.verify(spyProductions).englishToGeorgian(t2)
        inOrder.verify(spyProductions).englishToGeorgian(t3)
        // verify that a RTQ was made for each
        inOrder.verify(spyProductions).georgianToEnglish(t1)
        inOrder.verify(spyProductions).georgianToEnglish(t2)
        inOrder.verify(spyProductions).georgianToEnglish(t3)
    }
}