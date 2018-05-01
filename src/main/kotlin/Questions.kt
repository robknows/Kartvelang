/*Created on 29/04/18. */

class Questions {
    val set: MutableList<TranslateQuestion> = mutableListOf()

    constructor()
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
            listOf(phrase_whatareyoucalled, phrase_howareyou, mood_alright, mood_notbad)))
}