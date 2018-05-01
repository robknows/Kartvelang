/*Created on 29/04/18. */
import org.json.JSONArray
import java.io.File

class Questions {
    val set: MutableList<TranslateQuestion> = mutableListOf()

    constructor()
    constructor(filename: String) {
        val bufferedReader = File(filename).inputStream().reader().buffered()
        val text = bufferedReader.readText()
        bufferedReader.close()

        val jsonQuestions = JSONArray(text)
        for (i in 0..(jsonQuestions.length() - 1)) {
            val q = jsonQuestions.getJSONObject(i)
            set.add(TranslateQuestion(q.getString("question"), q.getString("answer")))
        }
    }
    constructor(qs: Collection<TranslateQuestion>) {
        set.addAll(qs)
    }

    fun add(question: TranslateQuestion) {
        set.add(question)
    }

    fun count(): Int {
        return set.count()
    }

    fun pop(): TranslateQuestion {
        return if (count() < 1) {
            NullTranslateQuestion
        } else {
            set.removeAt(0)
        }
    }

    fun empty(): Boolean {
        return set.isEmpty()
    }

    fun insertDelayed(q: TranslateQuestion) {
        if (set.count() < 4) {
            add(q)
        } else {
            set.add(3, q)
        }
    }
}

fun elevator_smalltalk(): Questions {
    val p = Productions()
    return Questions(p.introductionQuestions(
            listOf(greeting_hello, greeting_nicetomeetyou),
            listOf(farewell_seeyousoon),
            listOf(name_Keti),
            listOf(phrase_whatareyoucalled, mood_alright, mood_notbad)))
}