/*Created on 01/05/18. */
package logic

data class Translation(val english: String, val georgian: String)

// Alphabet
val alphabet = Translation("abcdefghijklmnopqrstuvwxyz", "აბგდევზთიკლმნოპჟრსტუფქღყშჩცძწჭხჯჰ")

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
