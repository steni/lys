package no.denindresirkel.lys

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.thingml.tradfri.LightBulb
import org.thingml.tradfri.TradfriBulbListener
import org.thingml.tradfri.TradfriGatewayListener
import javax.servlet.http.HttpServletRequest

class BulbService : TradfriGatewayListener, TradfriBulbListener {
    private val bulbView:HashMap<Int?, Bulb> = HashMap()
    private val bulbModel:HashMap<Int?, LightBulb?> = HashMap()
    private val gson: Gson = GsonBuilder().setPrettyPrinting().create()

    fun jsonMap(request: HttpServletRequest): String =
            gson.toJson(
                    bulbView.values.map {
                        val scheme = request.scheme
                        val server = request.serverName
                        val port = request.serverPort
                        val id = it.id
                        it.copy(href = "$scheme://$server:$port/bulb/$id")})

    override fun bulbDiscovered(b: LightBulb?) {
        addBulb(b)
    }

    override fun bulbStateChanged(bulb: LightBulb?) {
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
