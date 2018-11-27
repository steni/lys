package no.denindresirkel.lys

import java.util.function.Predicate

class Condition private constructor(private val predicate: Predicate<Any>, private val value: Any) {

    fun test(): Boolean {
        return predicate.test(value)
    }

    companion object {
        fun from(predicate: ((Int) -> Boolean)?, value: Any): Condition {
            return Condition(predicate, value)
        }

    }
}