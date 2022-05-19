package no.bekk.power.controller.customer

import no.bekk.power.controller.customer.dto.CreateCustomerRequest
import no.bekk.power.customer.Customer
import no.bekk.power.usecase.customer.CreateCustomerUseCase
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
class CustomerController(private val createCustomerUseCase: CreateCustomerUseCase) {

    private val log: Logger = LoggerFactory.getLogger(this.javaClass.name)

    @GetMapping("/customer/{id}")
    fun getCustomer(@PathVariable("id") customerId: String): ResponseEntity<Any> {
        return ResponseEntity.ok(customerId)
    }

    @PostMapping("/customer")
    fun createCustomer(@RequestBody createCustomerRequest: CreateCustomerRequest): ResponseEntity<Any> {
        val customer: Customer = createCustomerUseCase.create(
            name = createCustomerRequest.name,
            customerId = createCustomerRequest.customerId,
            country = createCustomerRequest.country
        )

        log.info("Customer with id ${customer.id} created")

        return ResponseEntity.status(HttpStatus.CREATED).build()
    }
}
