package no.bekk.power.application.customers

import no.bekk.power.application.CommandHandler
import no.bekk.power.domain.customer.CustomerFactory
import no.bekk.power.domain.customer.CustomerRepository
import no.bekk.power.domain.valuetypes.Country
import no.bekk.power.domain.valuetypes.CustomerId

data class CreateCustomer(val name: String, val customerId: CustomerId, val country: Country) {

    companion object {
        fun with(name: String, customerId: String, country: String): CreateCustomer {
            return CreateCustomer(
                name = name,
                customerId = CustomerId(customerId),
                country = Country(country)
            )
        }
    }
}

class CreateCustomerHandler(private val customerRepository: CustomerRepository) : CommandHandler<CreateCustomer> {

    override fun handle(command: CreateCustomer) {
        val (name, customerId, country) = command

        if (customerRepository.findByCustomerId(customerId) != null) {
            throw IllegalStateException("Customer with id $customerId already exists")
        }

        val customer = CustomerFactory.create(name, customerId, country)

        customerRepository.save(customer)
    }
}