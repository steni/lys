package no.denindresirkel.lys

class Transitioner {
    fun execute(transition: Transition) {
        val bulb = transition.bulb
        val oldState = bulb.state
        val newState = transition.state

        // create transition steps
        bulb.state = newState

    }

}
