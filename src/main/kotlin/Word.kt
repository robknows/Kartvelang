/*Created on 01/05/18. */
data class Word(val english: String, val georgian: String)

fun String.excludeIndex(i: Int): String {
    return when (i) {
        0 -> drop(1)
        length - 1 -> dropLast(1)
        else -> take(i - 1) + drop(i)
    }
}

val alphabet = Word("abcdefghijklmnopqrstuvwxyz", "აბგდევზთიკლმნოპჟრსტუფქღყშჩცძწჭხჯჰ")

val greeting_hello = Word("hello", "გამარჯობა")
val greeting_nicetomeetyou = Word("nice to meet you", "სასიამოვნოა")

val farewell_seeyousoon = Word("see you soon", "ნახვამდის")

val name_Keti = Word("Keti", "ქეთი")

val phrase_whatareyoucalled = Word("what are you called", "შენ რა გქვია")
val phrase_howareyou = Word("how are you", "როგორ ხარ")

val mood_notbad = Word("not bad", "არამიშავს")
val mood_alright = Word("alright", "კარგად")
