/*Created on 01/05/18. */
package logic

open class Productions {
    fun introductionQuestions(greetings: List<Translation>, farewells: List<Translation>, names: List<Translation>, phrases: List<Translation>): List<TranslationQuestion> {
        val directTranslations = concat(greetings, phrases, farewells).map(this::englishToGeorgian)

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

    open fun englishToGeorgian(w: Translation): TranslationQuestion {
        return TranslationQuestion(w.english, w.georgian)
    }

    open fun georgianToEnglish(w: Translation): TranslationQuestion {
        return TranslationQuestion(w.georgian, w.english)
    }

    fun introduction(greeting: Translation, name: Translation): TranslationQuestion {
        return TranslationQuestion("${greeting.english}, I am called ${name.english}", "${greeting.georgian}, მე მქვია ${name.georgian}")
    }

    fun farewell(farewell: Translation, name: Translation): TranslationQuestion {
        return TranslationQuestion("${farewell.english} ${name.english}", "${farewell.georgian} ${name.georgian}")
    }

    open fun alphabetSound(eng: Char, inWord: String, kar: Char, incorrect: Triple<Char, Char, Char>): MultipleChoiceQuestion {
        return MultipleChoiceQuestion("makes a sound like \"$eng\" in \"$inWord\"", kar.toString(), incorrect.toStrings(), randomChoice())
    }

    open fun englishToGeorgianMultipleChoiceTranslation(eng: String, kar: String, incorrect: Triple<String, String, String>): MultipleChoiceQuestion {
        return MultipleChoiceQuestion("means \"$eng\" in georgian", kar, incorrect, randomChoice())
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
