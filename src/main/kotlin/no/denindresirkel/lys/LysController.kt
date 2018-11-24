package no.denindresirkel.lys

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

/**
 * ScratchX extension are not able to POST to the server, thus we do not follow normal procedure
 */
@RestController
@CrossOrigin(origins = ["*"])
class LysController @Autowired constructor(
        private val bulbs: BulbLogic
) {
    /* Get all bulbs */
    @GetMapping("/bulbs", produces = ["application/json"])
    fun bulbs(request: HttpServletRequest) = bulbs.jsonMap(request)

    /* Turn all bulbs on */
    @GetMapping("/bulbs/on", produces = ["application/json"])
    fun allBulbsOn() = bulbs.allBulbsOn()

    /* Turn all bulbs off */
    @GetMapping("/bulbs/off", produces = ["application/json"])
    fun allBulbsOff() = bulbs.allBulbsOff()

    /* Get bulb by name or id */
    @GetMapping("/bulb/{nameOrId}")
    fun bulbById(@PathVariable("nameOrId") nameOrId: String): BulbView? =
            bulbs.getBulb(nameOrId)

    /* Turn specified bulb on */
    @GetMapping("/bulb/{nameOrId}/on")
    fun turnBulbOn(@PathVariable("nameOrId") nameOrId: String): String {
        bulbs.getBulbForUpdate(nameOrId)?.isOn = true
        return "success"
    }

    /* Turn specified bulb off */
    @GetMapping("/bulb/{nameOrId}/off")
    fun turnBulbOff(@PathVariable("nameOrId") nameOrId: String): String {
        bulbs.getBulbForUpdate(nameOrId)?.isOn = false
        return "success"
    }

    /* Set specified bulb's intensity */
    @GetMapping("/bulb/{nameOrId}/intensity/{intensity}")
    fun setIntensity(@PathVariable("nameOrId") nameOrId: String,
                     @PathVariable("intensity") intensity: Int): String {
        bulbs.getBulbForUpdate(nameOrId)?.intensity = intensity
        return "success"
    }

    /* Set specified bulb's color */
    @GetMapping("/bulb/{nameOrId}/color/{color}")
    fun setColor(@PathVariable("nameOrId") nameOrId: String,
                 @PathVariable("color") color: String): String {
        bulbs.getBulbForUpdate(nameOrId)?.color = color
        return "success"
    }

    /* Set specified bulb's color by r, g, b */
    @GetMapping("/bulb/{nameOrId}/color/r/{r}/g/{g}/b/{b}")
    fun setColorRgb(@PathVariable("nameOrId") nameOrId: String,
                    @PathVariable("r") r: Int,
                    @PathVariable("g") g: Int,
                    @PathVariable("b") b: Int): String {
        bulbs.getBulbForUpdate(nameOrId)?.setRGBColor(r, g, b)
        return "success"
    }
}