/*Created on 01/05/18. */
import junit.framework.TestCase.assertEquals
import org.junit.Test

class WordTest {
    @Test
    fun canLoadQuestionsFromFile() {
        val wl = WordLoader()
        val filename = "../../src/test/resources/elevator_smalltalk_words.json"
        val words: WordLoader.LoadedWords = wl.load(filename)
        val greetings = words.greetings
        val farewells = words.farewells
        val phrases = words.phrases
        val names = words.names

        assertEquals("hello", greetings[0].english)
        assertEquals("გამარჯობა", greetings[0].georgian)
        assertEquals("nice to meet you", greetings[1].english)
        assertEquals("სასიამოვნუა", greetings[1].georgian)

        assertEquals("see you soon", farewells[0].english)
        assertEquals("ნახვამდის", farewells[0].georgian)

        assertEquals("what are you called", phrases[0].english)
        assertEquals("შენ რა გქვია", phrases[0].georgian)
        assertEquals("how are you", phrases[1].english)
        assertEquals("როგორ ხარ", phrases[1].georgian)

        assertEquals("Keti", names[0].english)
        assertEquals("ქეთი", names[0].georgian)
    }
}