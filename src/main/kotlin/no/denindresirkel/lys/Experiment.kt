package no.denindresirkel.lys

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class Experiment @Autowired constructor(
        private val bulbs: BulbLogic
) {
    fun test() {
        val transitioner = Transitioner()
        val bulb = bulbs.getKotlinBulb(1)

        val tenSeconds = Duration.ofSeconds(10)
        val alreadyOn = Condition.from(bulb?.intensityGT, 0)
        val state = State(true, "ffffff", 254)
        if (bulb != null) {
            val transition = Transition(bulb).to(state).using(tenSeconds).iff(alreadyOn)
            transitioner.execute(transition)
        }
    }
}



