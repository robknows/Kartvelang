/*Created on 01/05/18. */
package logic.language

import org.json.JSONArray
import java.io.File

class TranslationLoader {
    fun load(filename: String): LoadedTranslations {
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
            when (w.getString("tag")) {
                "greeting" -> greetings.add(Translation(english, georgian))
                "farewell" -> farewells.add(Translation(english, georgian))
                "name" -> names.add(Translation(english, georgian))
                "phrase" -> phrases.add(Translation(english, georgian))
            }
        }
        return LoadedTranslations(greetings, farewells, names, phrases)
    }

    data class LoadedTranslations(val greetings: List<Translation>, val farewells: List<Translation>, val names: List<Translation>, val phrases: List<Translation>)
}
