package no.denindresirkel.lys

import org.thingml.tradfri.LightBulb

object LightBulbMarshaller {
    fun from(b: LightBulb?): State? {
        if (b != null) {
            return State(b.id, b.name, b.isOn, b.color, b.intensity)
        }
        return null
    }
}
