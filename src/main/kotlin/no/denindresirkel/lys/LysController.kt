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
    private final val logic = BulbLogic()

    init {
        gateway.startTradfriGateway()
        gateway.addTradfriGatewayListener(logic)
    }

    @GetMapping("/greeting")
    fun greeting(@RequestParam(value = "name", defaultValue = "World") name: String) =
            Greeting(counter.incrementAndGet(), "Hello, $name")

    @GetMapping("/bulbs",  produces = ["application/json"])
    fun bulbs() = logic.jsonMap()

    @RequestMapping(value = ["/bulb/{nameOrId}"], method = [RequestMethod.GET])
    fun getCustomerById(@PathVariable("nameOrId") nameOrId: String): Bulb? {
        return logic.getBulb(nameOrId)
    }

    @PostMapping("/bulb")
    fun setBulb(@RequestBody bulb : Bulb): Bulb? {
        val b = logic.getBulbForUpdate((bulb.id))
        b?.isOn = !(b?.isOn)!!
        return logic.getBulb(bulb.id!!)
    }


}