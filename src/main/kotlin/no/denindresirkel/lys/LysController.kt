package no.denindresirkel.lys

import org.springframework.web.bind.annotation.*
import org.thingml.tradfri.GatewayConfiguration
import org.thingml.tradfri.TradfriGateway
import java.util.concurrent.atomic.AtomicLong

@RestController
class LysController {

    val counter = AtomicLong()
    private final val config = GatewayConfiguration()
    private final val gateway = TradfriGateway(config.gatewayIp, config.securityKey)
    private final val gwListener = GWListener()

    init {
        gateway.startTradfriGateway()
        gateway.addTradfriGatewayListener(gwListener)
    }

    @GetMapping("/greeting")
    fun greeting(@RequestParam(value = "name", defaultValue = "World") name: String) =
            Greeting(counter.incrementAndGet(), "Hello, $name")

    @GetMapping("/bulbs",  produces = ["application/json"])
    fun bulbs() = gwListener.jsonMap()

    @RequestMapping(value = ["/bulb/{nameOrId}"], method = [RequestMethod.GET])
    fun getCustomerById(@PathVariable("nameOrId") nameOrId: String): Bulb? {
        return getBulb(nameOrId)
    }

    @PostMapping("/bulb")
    fun setBulb(@RequestBody bulb : Bulb): Bulb? {
        val b = getBulb((bulb.id.toString()))
        b?.color  = bulb.color
        return b
    }

    private fun getBulb(nameOrId: String): Bulb? {
        return try {
            val id = nameOrId.toInt()
            gwListener.getBulb(id)
        } catch (e: NumberFormatException) {
            gwListener.getBulb(nameOrId)
        }
    }



}