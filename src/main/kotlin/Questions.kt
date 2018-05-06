/*Created on 29/04/18. */

class Questions {
    val translationQuestions: MutableList<TranslationQuestion> = mutableListOf()
    val multipleChoiceQuestions: MutableList<MultipleChoiceQuestion> = mutableListOf()

    constructor()
    constructor(qs: Collection<TranslationQuestion>) {
        translationQuestions.addAll(qs)
    }

    constructor(translationQs: Collection<TranslationQuestion>, mcQs: Collection<MultipleChoiceQuestion>) {
        translationQuestions.addAll(translationQs)
        multipleChoiceQuestions.addAll(mcQs)
    }

    fun count(): Int {
        return translationQuestions.count() + multipleChoiceQuestions.count()
    }

    fun empty(): Boolean {
        return translationQuestions.isEmpty() && multipleChoiceQuestions.isEmpty()
    }

    fun add(question: TranslationQuestion) {
        translationQuestions.add(question)
    }

    fun pop(): TranslationQuestion {
        return if (count() < 1) {
            NullTranslationQuestion
        } else {
            translationQuestions.removeAt(0)
        }
    }

    fun insertDelayed(q: TranslationQuestion) {
        if (translationQuestions.count() < 4) {
            add(q)
        } else {
            translationQuestions.add(3, q)
        }
    }
}