package no.bekk.power.application.customer

import no.bekk.power.domain.customer.CustomerFactory
import no.bekk.power.domain.customer.CustomerRepository
import no.bekk.power.domain.valuetypes.CustomerId

class CreateCustomerUseCase(private val customerRepository: CustomerRepository) {

    fun create(name: String, customerId: String, country: String): Triple<Boolean, CustomerId, String> {
        if (customerRepository.findByCustomerId(CustomerId(customerId)) != null) {
            return Triple(false, CustomerId(customerId), "Customer with id $customerId already exists")
        }

        val customer = CustomerFactory.create(
            name = name,
            customerId = customerId,
            country = country
        )

        customerRepository.save(customer)

        return Triple(true, customer.id, "Customer created)")
    }
}
