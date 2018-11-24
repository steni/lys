package no.denindresirkel.lys

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
class BlockController {

    @GetMapping("/lys.js", produces = ["application/javascript"])
    fun blocks(request: HttpServletRequest): String {
        val classLoader = javaClass.classLoader
        val resource = classLoader.getResource("js/blocks.js")
        val txt = resource.readText(Charsets.UTF_8)

        val scheme = request.scheme
        val server = request.serverName
        val port = request.serverPort

        val href = "$scheme://$server:$port/"
        
        return txt.replace("https://127.0.0.1:8443/", href)
    }
}