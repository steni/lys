package no.denindresirkel.lys

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class Experiment @Autowired constructor(
    private val bulbs: BulbLogic
) {
    fun test() {
        val bulb = bulbs.getKotlinBulb(1)

        val tenSeconds = Duration.ofSeconds(10)
        val alreadyOn = Condition.from(bulb?.intensityGT, 0)
        Transition(bulb).set(state).using(tenSeconds).iff(alreadyOn).start()
    }


}



