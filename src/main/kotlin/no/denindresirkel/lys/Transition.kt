package no.denindresirkel.lys

import java.time.Duration

class Transition(val bulb: Bulb) {
    var fromState: State
    lateinit var toState: State    /* Required destination state */
    var time: Duration? = null
    var response: Response = Response.UNINITIALIZED
    private val conditions: MutableList<Condition> = mutableListOf()

    enum class Response {
        OK, INITIATED, RUNNING, FINISHED, CONDITIONS_NOT_MET, FAILED, UNINITIALIZED
    }

    init {
        fromState = bulb.state
    }

    fun from(state: State): Transition {
        this.fromState = state
        return this
    }

    fun to(state: State): Transition {
        this.toState = state
        return this
    }

    fun using(time: Duration): Transition {
        this.time = time
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

    fun validate() {
        checkNotNull(toState)
    }
}
