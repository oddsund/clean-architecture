package no.bekk.power.controller.customer

import no.bekk.power.controller.customer.dto.CreateCustomerRequest
import no.bekk.power.application.customer.CreateCustomerUseCase
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
class CustomersController(private val createCustomerUseCase: CreateCustomerUseCase) {

    private val log: Logger = LoggerFactory.getLogger(this.javaClass.name)

    @GetMapping("/customers/{id}")
    fun getCustomer(@PathVariable("id") customerId: String): ResponseEntity<Any> {
        return ResponseEntity.ok(customerId)
    }

    @PostMapping("/customers")
    fun createCustomer(@RequestBody createCustomerRequest: CreateCustomerRequest): ResponseEntity<Any> {
        val (success, customerId, message) = createCustomerUseCase.create(
            name = createCustomerRequest.name,
            customerId = createCustomerRequest.customerId,
            country = createCustomerRequest.country
        )

        if (success) {
            log.info("Customer with id $customerId created")
            return ResponseEntity.created(URI.create("/customer/${customerId.value}")).build()
        }

        log.error(message)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message)
    }
}
