package no.denindresirkel.lys

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class Experiment @Autowired constructor(
    private val bulbs: BulbLogic
) {
    fun test() {
        val bulb = bulbs.getBulbForUpdate("name")
        val state = State()
        bulb?.transitionTo(state)
    }
}

class State {

}
