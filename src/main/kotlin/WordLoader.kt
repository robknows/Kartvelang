/*Created on 01/05/18. */
import org.json.JSONArray
import java.io.File

class WordLoader {
    fun load(filename: String): LoadedWords {
        val bufferedReader = File(filename).inputStream().reader().buffered()
        val text = bufferedReader.readText()
        bufferedReader.close()

        val greetings = mutableListOf<Translation>()
        val farewells = mutableListOf<Translation>()
        val names = mutableListOf<Translation>()
        val phrases = mutableListOf<Translation>()

        val jsonWords = JSONArray(text)
        for (i in 0..(jsonWords.length() - 1)) {
            val w = jsonWords.getJSONObject(i)!!
            val english = w.getString("english")
            val georgian = w.getString("georgian")
            when(w.getString("tag")) {
                "greeting" -> greetings.add(Translation(english, georgian))
                "farewell" -> farewells.add(Translation(english, georgian))
                "name" -> names.add(Translation(english, georgian))
                "phrase" -> phrases.add(Translation(english, georgian))
            }
        }
        return LoadedWords(greetings, farewells, names, phrases)
    }

    data class LoadedWords(val greetings: List<Translation>, val farewells: List<Translation>, val names: List<Translation>, val phrases: List<Translation>)
}
