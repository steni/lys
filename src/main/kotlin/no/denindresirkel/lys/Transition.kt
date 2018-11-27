package no.denindresirkel.lys

import java.time.temporal.TemporalAmount

class Transition(val bulb: Bulb) {
    private val conditions: MutableList<Condition> = mutableListOf()

    lateinit var state: State

    fun to(state: State): Transition {
        this.state = state
        return this
    }

    fun using(time: TemporalAmount): Transition {
        return this
    }

    fun iff(condition: Condition): Transition {
        conditions.add(condition)
        return this
    }

    fun add(condition: Condition): Transition {
        return iff(condition)
    }

    fun conditionsMet(): Boolean {
        return allConditionsMet() || noConditions()
    }

    private fun noConditions(): Boolean {
        return conditions.size == 0
    }

    private fun allConditionsMet(): Boolean {
        return conditions.all { it.test() }
    }
}
