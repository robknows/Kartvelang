/*Created on 01/05/18. */
package logic

data class Translation(val english: String, val georgian: String)

// General
val alphabet = Translation("abcdefghijklmnopqrstuvwxyz", "აბგდევზთიკლმნოპჟრსტუფქღყშჩცძწჭხჯჰ")
val name_Keti = Translation("Keti", "ქეთი")

// Greetings 1
val greeting_hello = Translation("hello", "გამარჯობა")
val greeting_nicetomeetyou = Translation("nice to meet you", "სასიამოვნოა")
val phrase_whatareyoucalled = Translation("what are you called", "შენ რა გქვია")
val phrase_howareyou = Translation("how are you", "როგორ ხარ")
val mood_well = Translation("alright", "კარგად")
val mood_notbad = Translation("not bad", "არამიშავს")
val farewell_seeyousoon = Translation("see you soon", "ნახვამდის")

//