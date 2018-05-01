/*Created on 01/05/18. */
import junit.framework.TestCase
import junit.framework.TestCase.assertEquals
import org.junit.Test

class ProductionsTest {
    val p = Productions()
    val greeting_hello = Word("hello", "გამარჯობა")
    val name_Keti = Word("Keti", "ქეთი")


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
}