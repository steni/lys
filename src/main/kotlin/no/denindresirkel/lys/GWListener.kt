package no.denindresirkel.lys

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.thingml.tradfri.LightBulb
import org.thingml.tradfri.TradfriGatewayListener

class GWListener : TradfriGatewayListener {
    private val bulbs:HashMap<Int?, Bulb> = HashMap()
    private val gson: Gson = GsonBuilder().setPrettyPrinting().create()

    fun jsonMap(): String = gson.toJson(bulbs.values)

    override fun bulb_discovered(b: LightBulb?) {
        bulbs[b?.id] = Bulb(b?.id, b?.name, b?.isOn, b?.color) // TODO: store a view on the bulb, ´cause it's been updated
    }

    fun getBulb(id: Int): Bulb? {
        return bulbs[id]
    }

    fun getBulb(name: String): Bulb? {
        return bulbs.filter {
            it.value.name == name
        }.values.first()
    }

    override fun gateway_initializing() {
    }

    override fun bulb_discovery_started(total_devices: Int) {
    }

    override fun bulb_discovery_completed() {
    }

    override fun gateway_started() {
    }

    override fun gateway_stoped() {
    }

    override fun polling_started() {
    }

    override fun polling_completed(bulb_count: Int, total_time: Int) {
    }
}
