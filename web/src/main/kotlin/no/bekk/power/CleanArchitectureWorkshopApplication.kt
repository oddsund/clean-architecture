package no.bekk.power

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CleanArchitectureWorkshopApplication

// TODO: Should this just be a part of web? Makes it simpler with Spring also?
// Web knows about "everything"
fun main(args: Array<String>) {
	runApplication<CleanArchitectureWorkshopApplication>(*args)
}
