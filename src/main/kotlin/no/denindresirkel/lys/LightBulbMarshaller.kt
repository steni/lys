package no.denindresirkel.lys

import org.thingml.tradfri.LightBulb

object LightBulbMarshaller {
    fun stateFrom(b: LightBulb?): State {
        if (b != null) {
            return State(b.isOn, if (b.color == null) {""} else {b.color}, b.intensity)
        }
        return State()
    }
}
