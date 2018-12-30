package no.denindresirkel.lys

import org.thingml.tradfri.LightBulb

class Bulb(val backingBulb: LightBulb)  {
    var state: State by BulbStateDelegate

    val intensityGT: (Int) -> Boolean = { intensity: Int -> (intensity > state.intensity) }
    val intensityLT: (Int) -> Boolean = { intensity: Int -> (intensity < state.intensity) }
    val intensityEQ: (Int) -> Boolean = { intensity: Int -> (intensity == state.intensity) }

}