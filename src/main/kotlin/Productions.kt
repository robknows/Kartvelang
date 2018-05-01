/*Created on 01/05/18. */
class Productions {
    fun introductionQuestions(greetings: List<Word>, farewells: List<Word>, names: List<Word>, phrases: List<Word>): List<TranslateQuestion> {
        val directTranslations = concat(greetings, phrases, farewells).map(this::dictionary)

        val greetingsTranslations = mutableListOf<TranslateQuestion>()
        val farewellsTranslations = mutableListOf<TranslateQuestion>()
        for (name in names) {
            for (greeting in greetings) {
                greetingsTranslations.add(introduction(greeting, name))
            }
            for (farewell in farewells) {
                farewellsTranslations.add(farewell(farewell, name))
            }
        }
        return concat(directTranslations, greetingsTranslations, farewellsTranslations)
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

private fun <E> concat(vararg c: List<E>): List<E> {
    return c.map({ l -> l.toMutableList() }).reduce({ acc, nxt -> acc.concatList(nxt) }).toList()
}
