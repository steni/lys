package no.denindresirkel.lys

import org.springframework.web.bind.annotation.*
import org.thingml.tradfri.GatewayConfiguration
import org.thingml.tradfri.TradfriGateway
import java.util.concurrent.atomic.AtomicLong
import javax.servlet.http.HttpServletRequest

@RestController
class LysController {

    val counter = AtomicLong()
    private final val config = GatewayConfiguration()
    private final val gateway = TradfriGateway(config.gatewayIp, config.securityKey)
    private final val bulbService = BulbService()

    init {
        gateway.startTradfriGateway()
        gateway.addTradfriGatewayListener(bulbService)
    }

    @GetMapping("/greeting")
    fun greeting(@RequestParam(value = "name", defaultValue = "World") name: String) =
            Greeting(counter.incrementAndGet(), "Hello, $name")

    @GetMapping("/bulbs",  produces = ["application/json"])
    fun bulbs(request: HttpServletRequest) = bulbService.jsonMap(request)

    @RequestMapping(value = ["/bulb/{nameOrId}"], method = [RequestMethod.GET])
    fun getCustomerById(@PathVariable("nameOrId") nameOrId: String): Bulb? {
        return bulbService.getBulb(nameOrId)
    }

    @PostMapping("/bulb")
    fun setBulb(@RequestBody bulb : Bulb): Bulb? {
        val b = bulbService.getBulbForUpdate((bulb.id))
        b?.isOn = bulb.isOn!!
        b?.color = bulb.color
        return bulbService.getBulb(bulb.id!!)
    }


}