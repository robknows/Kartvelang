/*Created on 01/05/18. */
class Productions {
    companion object {
        fun introduction (greeting: Word, name: Word): TranslateQuestion {
            return TranslateQuestion("${greeting.english}, I am called ${name.english}", "${greeting.georgian}, მე მქვია ${name.georgian}")
        }
    }
}