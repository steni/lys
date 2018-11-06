package no.denindresirkel.lys

import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@RestController
class LysController {
    private final val bulbs = BulbLogic()

    /**
     * Get all bulbs
     */
    @CrossOrigin(origins = ["*"])
    @GetMapping("/bulbs", produces = ["application/json"])
    fun bulbs(request: HttpServletRequest) = bulbs.jsonMap(request)

    /**
     * Turn all bulbs on
     */
    @CrossOrigin(origins = ["*"])
    @GetMapping("/bulbs/on", produces = ["application/json"])
    fun allBulbsOn() {
        bulbs.allBulbsOn()
    }

    @CrossOrigin(origins = ["*"])
    @GetMapping("/bulbs/off", produces = ["application/json"])
    fun allBulbsOff() {
        bulbs.allBulbsOff()
    }

    @CrossOrigin(origins = ["*"])
    @RequestMapping(value = ["/bulb/{nameOrId}"], method = [RequestMethod.GET])
    fun bulbById(@PathVariable("nameOrId") nameOrId: String): Bulb? {
        return bulbs.getBulb(nameOrId)
    }

    @CrossOrigin(origins = ["*"])
    @RequestMapping(value = ["/bulb/{nameOrId}/on"], method = [RequestMethod.GET])
    fun turnBulbOn(@PathVariable("nameOrId") nameOrId: String): String {
        val bulb = bulbs.getBulbForUpdate(nameOrId)
        bulb!!.isOn = true
        return "success"
    }

    @CrossOrigin(origins = ["*"])
    @RequestMapping(value = ["/bulb/{nameOrId}/off"], method = [RequestMethod.GET])
    fun turnBulbOff(@PathVariable("nameOrId") nameOrId: String): String {
        val bulb = bulbs.getBulbForUpdate(nameOrId)
        bulb!!.isOn = false
        return "success"
    }


    @PostMapping("/bulb")
    fun setBulb(@RequestBody bulb: Bulb): Bulb? {
        val b = bulbs.getBulbForUpdate(bulb.id)
        b?.isOn = bulb.isOn!!
        b?.color = bulb.color
        return bulbs.getBulb(bulb.id)
    }


}