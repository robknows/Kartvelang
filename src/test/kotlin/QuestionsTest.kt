/*Created on 29/04/18. */

import junit.framework.TestCase.assertEquals
import org.junit.Test

class QuestionsTest {
    @Test
    fun questionsInitialisesEmpty() {
        var questions = Questions();
        assertEquals(questions.count, 0);
    }
}