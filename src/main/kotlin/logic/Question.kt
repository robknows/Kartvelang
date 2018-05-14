/*Created on 02/05/18. */
package logic

interface Question {
    val answer: String
    val fullCorrections: Boolean

    fun verifyAnswer(attempt: String): Boolean
}