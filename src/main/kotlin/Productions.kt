/*Created on 01/05/18. */

class Productions {
    fun introductionQuestions(greetings: List<Translation>, farewells: List<Translation>, names: List<Translation>, phrases: List<Translation>): List<TranslationQuestion> {
        val directTranslations = concat(greetings, phrases, farewells).map(this::dictionary)

        val greetingsTranslations = mutableListOf<TranslationQuestion>()
        val farewellsTranslations = mutableListOf<TranslationQuestion>()
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

    fun dictionary(w: Translation): TranslationQuestion {
        return TranslationQuestion(w.english, w.georgian)
    }

    fun introduction(greeting: Translation, name: Translation): TranslationQuestion {
        return TranslationQuestion("${greeting.english}, I am called ${name.english}", "${greeting.georgian}, მე მქვია ${name.georgian}")
    }

    fun farewell(farewell: Translation, name: Translation): TranslationQuestion {
        return TranslationQuestion("${farewell.english} ${name.english}", "${farewell.georgian} ${name.georgian}")
    }

    fun alphabetSound(eng: Char, inWord: String, kar: Char, incorrect: Triple<Char, Char, Char>): MultipleChoiceQuestion {
        return MultipleChoiceQuestion("makes a sound like \"$eng\" in \"$inWord\"", kar.toString(), incorrect.toStrings(), randomChoice())
    }
}

private fun <E> MutableList<E>.concatList(phrases: Collection<E>): MutableList<E> {
    addAll(phrases)
    return this
}

private fun <E> concat(vararg c: List<E>): List<E> {
    return c.map({ l -> l.toMutableList() }).reduce({ acc, nxt -> acc.concatList(nxt) }).toList()
}

private fun <A, B, C> Triple<A, B, C>.toStrings(): Triple<String, String, String> {
    return Triple(first.toString(), second.toString(), third.toString())
}
