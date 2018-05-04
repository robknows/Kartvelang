/*Created on 04/05/18. */
import junit.framework.TestCase
import org.junit.Test

class MultipleChoiceOverlayTest {
    val o = MultipleChoiceOverlay()

    @Test
    fun canShowQuestion() {
        val q = MultipleChoiceQuestion("makes a sound like \"m\" in \"monkey\"", "მ", Triple("გ", "ლ", "ო"))

        o.showQuestion(q)

        TestCase.assertEquals("Which of these makes a sound like \"m\" in \"monkey\"?\n  მ    გ\n  ლ    ო", o.toString())
    }
}