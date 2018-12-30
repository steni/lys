package no.denindresirkel.lys

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

object BulbStateDelegate : ReadWriteProperty<Bulb, State> {
    override fun getValue(thisRef: Bulb, property: KProperty<*>): State {
        return LightBulbMarshaller.stateFrom(thisRef.backingBulb)
    }

    override fun setValue(thisRef: Bulb, property: KProperty<*>, value: State) {
        val receiver = thisRef.backingBulb
        with(receiver) {
            intensity = value.intensity
            color = value.color
            isOn = value.isOn
        }
    }
}
