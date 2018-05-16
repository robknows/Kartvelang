/*Created on 14/05/18. */
package course

import logic.*

/* Hello!

Learns how to say "გამარჯობა" + letters

Vocab:

გამარჯობა

 */
val lesson_hello = MemoLesson(Productions(),
        listOf(alphabet_g, alphabet_a, alphabet_m, alphabet_r, alphabet_j, alphabet_b),
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