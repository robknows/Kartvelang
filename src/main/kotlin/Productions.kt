/*Created on 01/05/18. */
class Productions {
    fun dictionary(w: Word): TranslateQuestion {
        return TranslateQuestion(w.english, w.georgian)
    }

    fun introduction(greeting: Word, name: Word): TranslateQuestion {
        return TranslateQuestion("${greeting.english}, I am called ${name.english}", "${greeting.georgian}, მე მქვია ${name.georgian}")
    }
}