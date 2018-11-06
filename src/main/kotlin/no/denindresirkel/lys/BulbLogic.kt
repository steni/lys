package no.denindresirkel.lys

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.thingml.tradfri.*
import javax.servlet.http.HttpServletRequest

class BulbLogic : TradfriGatewayListener, TradfriBulbListener {
    private val bulbView: HashMap<Int?, Bulb> = HashMap()
    private val bulbModel: HashMap<Int?, LightBulb?> = HashMap()
    private val gson: Gson = GsonBuilder().setPrettyPrinting().create()

    private val config = GatewayConfiguration()
    private val gateway = TradfriGateway(config.gatewayIp, config.securityKey)

    init {
        gateway.startTradfriGateway()
        gateway.addTradfriGatewayListener(this)
    }

    fun getBulbForUpdate(id: Int) = bulbModel[id]
    fun getBulb(id: Int): Bulb? = bulbView[id]

    fun getBulb(nameOrId: String): Bulb? {
        return try {
            val id = nameOrId.toInt()
            getBulb(id)
        } catch (e: NumberFormatException) {
            getBulbByName(nameOrId)
        }
    }

    fun getBulbForUpdate(nameOrId: String): LightBulb? {
        return try {
            val id = nameOrId.toInt()
            getBulbForUpdate(id)
        } catch (e: NumberFormatException) {
            getBulbForUpdateByName(nameOrId)
        }
    }

    fun allBulbsOn() {
        bulbModel.forEach { it.value?.isOn = true }
    }

    fun allBulbsOff() {
        bulbModel.forEach { it.value?.isOn = false }
    }

    private fun getBulbByName(name: String): Bulb? {
        return bulbView.filter {
            it.value.name == name
        }.values.first()
    }

    private fun getBulbForUpdateByName(name: String): LightBulb? {
        return bulbModel.filter {
            it.value?.name == name
        }.values.first()
    }

    fun jsonMap(request: HttpServletRequest): String =
            gson.toJson(
                    bulbView.values.map {
                        val scheme = request.scheme
                        val server = request.serverName
                        val port = request.serverPort
                        val id = it.id
                        it.copy(href = "$scheme://$server:$port/bulb/$id")
                    })

    override fun bulb_discovered(b: LightBulb?) {
        addBulb(b)
    }

    override fun bulb_state_changed(bulb: LightBulb?) {
        updateView(bulb)
    }

    private fun updateView(b: LightBulb?) {
        bulbView[b?.id] = Bulb(b!!.id, b.name, b.isOn, b.color)
    }

    private fun addBulb(b: LightBulb?) {
        bulbView[b?.id] = Bulb(b!!.id, b.name, b.isOn, b.color)
        bulbModel[b.id] = b
        b.addLightBulbListener(this)
    }

}
