package no.bekk.power

import no.bekk.power.api.config.ApiConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

@SpringBootApplication
@Import(ApiConfig::class)
class CleanArchitectureWorkshopApplication

fun main(args: Array<String>) {
	runApplication<CleanArchitectureWorkshopApplication>(*args)
}
