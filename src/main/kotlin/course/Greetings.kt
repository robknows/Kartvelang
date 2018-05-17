/*Created on 14/05/18. */
package course

import logic.language.*
import logic.lesson.MemoLesson
import logic.lesson.WordsToPhrasesLesson

/* Hello!

Learns how to say "გამარჯობა" + letters

Vocab:

გამარჯობა

 */
val lesson_hello = MemoLesson("hello", Productions(),
        listOf(letter_g, letter_a, letter_m, letter_r, letter_j, letter_b),
        listOf(greeting_hello))

/* What are you called?

Learns how to say "შენ რა გქვია?" + "მე მქვია ქეთი" + letters

1st and 2nd person singular personal pronouns: "მე" + "შენ"

Vocab:

მე
მქვია
შენ
გქვია
რა
სასიამოვნოა

 */

val lesson_whatareyoucalled = WordsToPhrasesLesson("what are you called?",
        Productions(),
        listOf(letter_sh, letter_e, letter_n, letter_q, letter_v, letter_i, letter_t),
        listOf(question_what, pronoun_1st_s_nom, pronoun_2nd_s_nom),
        listOf(
                Pair({ _: Translation -> Translation("What are you called?", "შენ რა გქვია?") }, listOf()),
                Pair({ name: Translation -> Translation("I am called ${name.english}", "მე ${name.georgian} მქვია") }, listOf(name_Keti, name_Tengo)))
)

/* How are you?

Learns how to say "როგორ ხარ?" + "კარგად" + "არამიშავს" + letters

Vocab:

როგორ
ხარ
გმადლობ
კარგად
არამიშავს
ნახვამდის

 */