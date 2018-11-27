package no.denindresirkel.lys

import org.thingml.tradfri.LightBulb
import java.time.temporal.TemporalAmount

class Transition(val bulb: LightBulb?) {
    private val conditions: MutableList<Condition> = mutableListOf()
    private lateinit var state: State

    fun set(state: State): Transition {
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

    fun start() {

    }
}
