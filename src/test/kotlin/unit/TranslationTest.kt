/*Created on 01/05/18. */
package unit

import junit.framework.TestCase.assertEquals
import logic.language.TranslationLoader
import org.junit.Test
import java.nio.file.Paths

class TranslationTest {
    @Test
    fun canLoadTranslationsFromFile() {
        val tl = TranslationLoader()
        val path = fp("src/test/resources/elevator_smalltalk_words.json")
        val translations: TranslationLoader.LoadedTranslations = tl.load(path)
        val greetings = translations.greetings
        val farewells = translations.farewells
        val phrases = translations.phrases
        val names = translations.names

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

fun fp(relativeToRootDir: String): String {
    val projectPath = Paths.get("").toAbsolutePath().toString()
    val fileRelativePath = if (projectPath.endsWith("modules")) {
        "../../$relativeToRootDir"
    } else {
        relativeToRootDir
    }
    return Paths.get(projectPath, fileRelativePath).toString()
}