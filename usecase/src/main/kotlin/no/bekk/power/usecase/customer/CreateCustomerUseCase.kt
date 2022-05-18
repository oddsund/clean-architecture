package no.bekk.power.usecase.customer

import no.bekk.power.customer.Customer
import no.bekk.power.customer.CustomerFactory
import no.bekk.power.customer.CustomerRepository
import java.lang.IllegalArgumentException

class CreateCustomerUseCase(private val customerRepository: CustomerRepository) {

    fun create(name: String, legalId: String, country: String): Customer {
        if (customerRepository.findByLegalId(legalId) != null) {
            throw IllegalArgumentException("Customer with id $legalId already exists")
        }

        val customer = CustomerFactory.create(
            name = name,
            legalId = legalId,
            country = country
        )

        // Save customer aggregate using CustomerRepository
        customerRepository.save(customer)
        return customer
    }
}
