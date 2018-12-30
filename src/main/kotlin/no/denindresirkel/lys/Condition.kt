package no.denindresirkel.lys

class Condition private constructor(private val predicate: ((Int) -> Boolean)?, private val value: Int) {

    fun test(): Boolean {
        if (predicate != null) {
            return predicate.invoke(value)
        }
        return false
    }

    companion object {
        fun from(predicate: ((Int) -> Boolean)?, value: Int): Condition {
            return Condition(predicate, value)
        }

    }
}