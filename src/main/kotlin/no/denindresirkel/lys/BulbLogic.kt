package no.denindresirkel.lys

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.springframework.stereotype.Component
import org.thingml.tradfri.*
import javax.servlet.http.HttpServletRequest

@Component
class BulbLogic : TradfriGatewayListener, TradfriBulbListener {
    private val bulbView: HashMap<Int, BulbView> = HashMap()
    private val bulbModel: HashMap<Int, LightBulb> = HashMap()
    private val kotlinBulb: HashMap<Int, Bulb> = HashMap() // developed in parallel for now

    private val gson: Gson = GsonBuilder().setPrettyPrinting().create()

    private val config = GatewayConfiguration()
    private val gateway = TradfriGateway(config.gatewayIp, config.securityKey)

    init {
        gateway.startTradfriGateway()
        gateway.addTradfriGatewayListener(this)
    }

    fun getBulb(id: Int): BulbView? = bulbView[id]

    fun getBulb(nameOrId: String): BulbView? {
        return try {
            val id = nameOrId.toInt()
            getBulb(id)
        } catch (e: NumberFormatException) {
            getBulbByName(nameOrId)
        }
    }

    fun getKotlinBulb(id: Int): Bulb? = kotlinBulb[id]

    fun getBulbForUpdate(id: Int) = bulbModel[id]

    fun getBulbForUpdate(nameOrId: String): LightBulb? {
        return try {
            val id = nameOrId.toInt()
            getBulbForUpdate(id)
        } catch (e: NumberFormatException) {
            getBulbForUpdateByName(nameOrId)
        }
    }

    fun allBulbsOn() {
        bulbModel.forEach { it.value.isOn = true }
    }

    fun allBulbsOff() {
        bulbModel.forEach { it.value.isOn = false }
    }

    private fun getBulbByName(name: String): BulbView? {
        return bulbView.filter {
            it.value.name == name
        }.values.first()
    }

    private fun getBulbForUpdateByName(name: String): LightBulb? {
        return bulbModel.filter {
            it.value.name == name
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

    override fun bulb_discovered(b: LightBulb) {
        addBulb(b)
    }

    override fun bulbStateChanged(bulb: LightBulb) {
        updateView(bulb)
    }

    private fun addBulb(b: LightBulb) {
        bulbView[b.id] = BulbView(b.id, b.name, b.isOn, b.color, b.intensity)
        bulbModel[b.id] = b
        b.addLightBulbListener(this)

        // new Kotlin class, created in parallel for now
        val state = State(b.isOn, b.color, b.intensity)
        val bulb = Bulb(state)
        b.addLightBulbListener(bulb)

    }

    private fun updateView(b: LightBulb) {
        bulbView[b.id] = BulbView(b.id, b.name, b.isOn, b.color, b.intensity)
    }

}
