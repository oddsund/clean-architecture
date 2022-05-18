package no.bekk.power.api.customer

import no.bekk.power.api.customer.dto.CreateCustomerRequest
import no.bekk.power.customer.Customer
import no.bekk.power.db.InMemoryCustomerRepository
import no.bekk.power.usecase.customer.CreateCustomerUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api")
class CustomerController {

    private val createCustomerUseCase = CreateCustomerUseCase(
        InMemoryCustomerRepository()
    )

    @PostMapping("/customer")
    fun createCustomer(@RequestBody createCustomerRequest: CreateCustomerRequest): ResponseEntity<Any> {
        // TODO Should we know about the domain types here? If not there will be a lot of mapping...

        val customer: Customer = createCustomerUseCase.create(
            name = createCustomerRequest.name,
            legalId = createCustomerRequest.legalId,
            country = createCustomerRequest.legalCountry
        )

        return ResponseEntity.status(HttpStatus.CREATED).build()
    }
}
