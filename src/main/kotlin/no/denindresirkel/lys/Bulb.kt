package no.denindresirkel.lys

import org.thingml.tradfri.LightBulb
import org.thingml.tradfri.TradfriBulbListener

class Bulb(var state: State ) : TradfriBulbListener {

    val intensityGT: (Int) -> Boolean = { intensity: Int -> (intensity > state.intensity) }
    val intensityLT: (Int) -> Boolean = { intensity: Int -> (intensity < state.intensity) }
    val intensityEQ: (Int) -> Boolean = { intensity: Int -> (intensity == state.intensity) }

    override fun bulbStateChanged(bulb: LightBulb?) {
        val state = LightBulbMarshaller.from(bulb)
        if (state != null ) this.state = state
    }
    
}