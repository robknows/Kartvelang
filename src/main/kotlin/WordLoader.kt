/*Created on 01/05/18. */
import org.json.JSONArray
import java.io.File

class WordLoader {
    fun load(filename: String): LoadedWords {
        val bufferedReader = File(filename).inputStream().reader().buffered()
        val text = bufferedReader.readText()
        bufferedReader.close()

        val greetings = mutableListOf<Word>()
        val farewells = mutableListOf<Word>()
        val names = mutableListOf<Word>()
        val phrases = mutableListOf<Word>()

        val jsonWords = JSONArray(text)
        for (i in 0..(jsonWords.length() - 1)) {
            val w = jsonWords.getJSONObject(i)!!
            val english = w.getString("english")
            val georgian = w.getString("georgian")
            when(w.getString("tag")) {
                "greeting" -> greetings.add(Word(english, georgian))
                "farewell" -> farewells.add(Word(english, georgian))
                "name"     -> names.add(Word(english, georgian))
                "phrase"   -> phrases.add(Word(english, georgian))
            }
        }
        return LoadedWords(greetings, farewells, names, phrases)
    }

    data class LoadedWords(val greetings: List<Word>, val farewells: List<Word>, val names: List<Word>, val phrases: List<Word>)
}
