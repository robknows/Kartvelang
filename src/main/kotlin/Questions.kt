/*Created on 29/04/18. */
import org.json.JSONArray
import java.io.File

class Questions {
    val set: MutableList<Question> = mutableListOf()

    constructor()
    constructor(filename: String) {
        val bufferedReader = File(filename).inputStream().reader().buffered()
        val text = bufferedReader.readText()
        bufferedReader.close()

        val jsonQuestions = JSONArray(text)
        for (i in 0..(jsonQuestions.length() - 1)) {
            val q = jsonQuestions.getJSONObject(i)
            set.add(Question(q.getString("question"), q.getString("answer")))
        }
    }

    fun add(question: Question) {
        set.add(question)
    }

    fun count(): Int {
        return set.count()
    }

    fun pop(): Question {
        return if (count() < 1) {
            NullQuestion
        } else {
            set.removeAt(0)
        }
    }

    fun empty(): Boolean {
        return set.isEmpty()
    }

    fun insertDelayed(q: Question) {
        if (set.count() < 4) {
            add(q)
        } else {
            set.add(3, q)
        }
    }
}
