/*Created on 01/05/18. */
class Productions {
    fun introductionQuestions(greetings: List<Word>, names: List<Word>, phrases: List<Word>): List<TranslateQuestion> {
        val directTranslations = greetings.toMutableList().concatList(phrases).map(this::dictionary)
        val introductions = mutableListOf<TranslateQuestion>()
        for (greeting in greetings) {
            for (name in names) {
                introductions.add(introduction(greeting, name))
            }
        }
        return introductions.concatList(directTranslations)
    }

    fun dictionary(w: Word): TranslateQuestion {
        return TranslateQuestion(w.english, w.georgian)
    }

    fun introduction(greeting: Word, name: Word): TranslateQuestion {
        return TranslateQuestion("${greeting.english}, I am called ${name.english}", "${greeting.georgian}, მე მქვია ${name.georgian}")
    }

    fun farewell(farewell: Word, name: Word): TranslateQuestion {
        return TranslateQuestion("${farewell.english} ${name.english}", "${farewell.georgian} ${name.georgian}")
    }
}

private fun <E> MutableList<E>.concatList(phrases: Collection<E>): MutableList<E> {
    addAll(phrases)
    return this
}
