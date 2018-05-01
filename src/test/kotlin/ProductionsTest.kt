/*Created on 01/05/18. */
import junit.framework.TestCase
import junit.framework.TestCase.assertEquals
import org.junit.Test

class ProductionsTest {
    val p = Productions()

    val greeting_hello = Word("hello", "გამარჯობა")
    val greeting_nicetomeetyou = Word("nice to meet you", "სასიამოვნოა")

    val farewell_seeyousoon = Word("see you soon", "ნახვამდის")

    val name_Keti = Word("Keti", "ქეთი")

    val phrase_whatareyoucalled = Word("what are you called", "შენ რა გქვია")


    @Test
    fun canProduceDirectTranslation() {
        val q = p.dictionary(greeting_hello)

        assertEquals("hello", q.given)
        assertEquals("გამარჯობა", q.answer)
    }

    @Test
    fun canProduceIntroduction() {
        val q = p.introduction(greeting_hello, name_Keti)

        TestCase.assertEquals("hello, I am called Keti", q.given)
        TestCase.assertEquals("გამარჯობა, მე მქვია ქეთი", q.answer)
    }

    @Test
    fun canProduceFarewell() {
        val q = p.farewell(farewell_seeyousoon, name_Keti)

        TestCase.assertEquals("see you soon Keti", q.given)
        TestCase.assertEquals("ნახვამდის ქეთი", q.answer)
    }

    @Test
    fun canAssembleIntroductionQuestions() {
        val greetings = listOf(greeting_hello, greeting_nicetomeetyou)
        val farewells = listOf(farewell_seeyousoon)
        val names = listOf(name_Keti)
        val phrases = listOf(phrase_whatareyoucalled)

        val qs = p.introductionQuestions(greetings, farewells, names, phrases)

        val q1 = TranslateQuestion("hello", "გამარჯობა")
        val q2 = TranslateQuestion("nice to meet you", "სასიამოვნოა")
        val q3 = TranslateQuestion("what are you called", "შენ რა გქვია")
        val q4 = TranslateQuestion("see you soon", "ნახვამდის")
        val q5 = TranslateQuestion("hello, I am called Keti", "გამარჯობა, მე მქვია ქეთი")
        val q6 = TranslateQuestion("nice to meet you, I am called Keti", "სასიამოვნოა, მე მქვია ქეთი")
        val q7 = TranslateQuestion("see you soon Keti", "ნახვამდის ქეთი")
        val expected = listOf(q1, q2, q3, q4, q5, q6, q7)

        for (i in 0..6) {
            assertEquals(expected[i].given, qs[i].given)
            assertEquals(expected[i].answer, qs[i].answer)
        }
        assertEquals(7, qs.count())
    }
}