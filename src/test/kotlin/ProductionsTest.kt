/*Created on 01/05/18. */
import junit.framework.TestCase
import junit.framework.TestCase.assertEquals
import org.junit.Test

class ProductionsTest {
    val greeting_hello = Word("hello", "გამარჯობა")
    val name_Keti = Word("Keti", "ქეთი")

    @Test
    fun canProduceDirectTranslation() {
        val q = Productions.dictionary(greeting_hello)

        assertEquals("hello", q.given)
        assertEquals("გამარჯობა", q.answer)
    }

    @Test
    fun canProduceIntroduction() {
        val q = Productions.introduction(greeting_hello, name_Keti)

        TestCase.assertEquals("hello, I am called Keti", q.given)
        TestCase.assertEquals("გამარჯობა, მე მქვია ქეთი", q.answer)
    }
}