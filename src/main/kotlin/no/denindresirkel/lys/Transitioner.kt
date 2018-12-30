package no.denindresirkel.lys

import java.util.*
import kotlin.math.abs

class Transitioner {
    var status : Transition.Response = Transition.Response.UNINITIALIZED

    fun execute(transition: Transition): Transition.Response {
        transition.validate()
        if (!transition.conditionsMet()) return Transition.Response.CONDITIONS_NOT_MET
        val intermediateStates: List<State> = createIntermediateStates(transition)
        walk(transition.bulb, intermediateStates, 1000 )
        // return a handle to the transition, where response probably is something like "INITIATED",
        // then "RUNNING", eventually turning into "FINISHED"
        return status
    }

    private fun createIntermediateStates(transition: Transition): List<State> {
        /* If we have no time to perform the transition, return new state as the only step */
        val time = transition.time ?: return listOf(transition.toState)

        val (aIsOn, aColor, aIntensity, _) = transition.fromState
        val (zIsOn, zColor, zIntensity, _) = transition.toState

        val transitionSeconds = time.seconds
        val deltaIntensity = abs(aIntensity - zIntensity)

        val intensityStepSize = deltaIntensity / transitionSeconds

        var steps = mutableListOf<State>()

        for (i in 0..transitionSeconds) {

        }


        val bulb = transition.bulb
        val currentIsOn = bulb.state.isOn
        val targetIsOn = transition.toState.isOn

        /*
        Are we turning lights on during this transition? If so, it needs to be the first thing we do, or else the
        rest of the transition will be invisible. Are we turning them off? In that case, it needs to be the last thing
        we do.
        */
        val turningOn = !currentIsOn && targetIsOn
        val turningOff = currentIsOn && !targetIsOn

//        if (turningOn) steps.add()

        return steps
    }

    private fun walk(bulb: Bulb, intermediateStates: List<State>, interval : Long) {
        val timer = Timer()
        val walker = Walker(bulb, intermediateStates)
        timer.scheduleAtFixedRate(walker, 0, interval)
    }

    class Walker(private val bulb: Bulb, private val states: List<State>) : TimerTask() {
        private var counter = 0

        override fun run() {
            if (counter < states.size) bulb.state = states[counter++]
        }

    }


}
