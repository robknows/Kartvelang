/*Created on 01/05/18. */
package logic

data class Translation(val english: String, val georgian: String)

// Alphabet
val alphabet = Translation("abcdefghijklmnopqrstuvwxyz", "აბგდევზთიკლმნოპჟრსტუფქღყშჩცძწჭხჯჰ")
val alphabet_a = Translation("a", "ა")
val alphabet_b = Translation("b", "ბ")
val alphabet_g = Translation("g", "გ")
val alphabet_m = Translation("m", "მ")
val alphabet_r = Translation("r", "რ")
val alphabet_j = Translation("j", "ჯ")
val alphabet_sh = Translation("sh", "შ")
val alphabet_q = Translation("q", "ქ")
val alphabet_v = Translation("v", "ვ")
val alphabet_i = Translation("i", "ი")
val alphabet_e = Translation("e", "ე")

// Names
val name_Keti = Translation("Keti", "ქეთი")
val name_Tengo = Translation("Tengo", "თენგო")

// Greetings
val greeting_hello = Translation("hello", "გამარჯობა")

// Question words
val question_what = Translation("what", "რა")

// Pronouns
val pronoun_1st_s_nom = Translation("I", "მე")
val pronoun_2nd_s_nom = Translation("you", "შენ")
