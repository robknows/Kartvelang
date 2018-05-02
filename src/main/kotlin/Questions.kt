/*Created on 29/04/18. */

class Questions {
    val translationQuestions: MutableList<TranslationQuestion> = mutableListOf()

    constructor()
    constructor(qs: Collection<TranslationQuestion>) {
        translationQuestions.addAll(qs)
    }

    fun add(question: TranslationQuestion) {
        translationQuestions.add(question)
    }

    fun count(): Int {
        return translationQuestions.count()
    }

    fun pop(): TranslationQuestion {
        return if (count() < 1) {
            NullTranslationQuestion
        } else {
            translationQuestions.removeAt(0)
        }
    }

    fun empty(): Boolean {
        return translationQuestions.isEmpty()
    }

    fun insertDelayed(q: TranslationQuestion) {
        if (translationQuestions.count() < 4) {
            add(q)
        } else {
            translationQuestions.add(3, q)
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