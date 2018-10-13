package no.denindresirkel.lys

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class LysApplication

fun main(args: Array<String>) {
    SpringApplication.run(LysApplication::class.java, *args)
}