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

// Names
val name_Keti = Translation("Keti", "ქეთი")
val name_Tengo = Translation("Tengo", "თენგო")

// Greetings
val greeting_hello = Translation("hello", "გამარჯობა")
val greeting_nicetomeetyou = Translation("nice to meet you", "სასიამოვნოა")
val phrase_whatareyoucalled = Translation("what are you called", "შენ რა გქვია")
val phrase_howareyou = Translation("how are you", "როგორ ხარ")
val mood_well = Translation("alright", "კარგად")
val mood_notbad = Translation("not bad", "არამიშავს")
val farewell_seeyousoon = Translation("see you soon", "ნახვამდის")