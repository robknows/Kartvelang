/*Created on 29/04/18. */

class Questions {
    val set: MutableList<TranslationQuestion> = mutableListOf()

    constructor()
    constructor(qs: Collection<TranslationQuestion>) {
        set.addAll(qs)
    }

    fun add(question: TranslationQuestion) {
        set.add(question)
    }

    fun count(): Int {
        return set.count()
    }

    fun pop(): TranslationQuestion {
        return if (count() < 1) {
            NullTranslationQuestion
        } else {
            set.removeAt(0)
        }
    }

    fun empty(): Boolean {
        return set.isEmpty()
    }

    fun insertDelayed(q: TranslationQuestion) {
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