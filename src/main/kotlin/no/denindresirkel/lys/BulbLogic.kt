package no.denindresirkel.lys

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.thingml.tradfri.LightBulb
import org.thingml.tradfri.TradfriBulbListener
import org.thingml.tradfri.TradfriGatewayListener

class BulbLogic : TradfriGatewayListener, TradfriBulbListener {
    private val bulbView:HashMap<Int?, Bulb> = HashMap()
    private val bulbModel:HashMap<Int?, LightBulb?> = HashMap()
    private val gson: Gson = GsonBuilder().setPrettyPrinting().create()

    fun jsonMap(): String = gson.toJson(bulbView.values)

    override fun bulb_discovered(b: LightBulb?) {
        addBulb(b)
    }

    override fun bulb_state_changed(bulb: LightBulb?) {
        updateView(bulb)
    }

    private fun updateView(b: LightBulb?) {
        bulbView[b?.id] = Bulb(b?.id, b?.name, b?.isOn, b?.color)
    }

    private fun addBulb(b: LightBulb?) {
        bulbView[b?.id] = Bulb(b?.id, b?.name, b?.isOn, b?.color)
        bulbModel[b?.id] = b
        b?.addLightBulbListener(this)
    }

    fun getBulb(nameOrId: String): Bulb? {
        return try {
            val id = nameOrId.toInt()
            getBulb(id)
        } catch (e: NumberFormatException) {
            getBulbByName(nameOrId)
        }
    }

    fun getBulbForUpdate(id: Int?): LightBulb? {
        return bulbModel[id]
    }

    fun getBulb(id: Int): Bulb? {
        return bulbView[id]
    }

    private fun getBulbByName(name: String): Bulb? {
        return bulbView.filter {
            it.value.name == name
        }.values.first()
    }

}
