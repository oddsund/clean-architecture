package no.bekk.power.application.customers

import no.bekk.power.application.CommandHandler
import no.bekk.power.domain.customer.CustomerFactory
import no.bekk.power.domain.customer.CustomerRepository
import no.bekk.power.domain.valuetypes.Country
import no.bekk.power.domain.valuetypes.CustomerId

data class CreateCustomerCommand(val name: String, val customerId: CustomerId, val country: Country) {

    companion object {
        fun with(name: String, customerId: String, country: String): CreateCustomerCommand {
            return CreateCustomerCommand(
                name = name,
                customerId = CustomerId(customerId),
                country = Country(country)
            )
        }
    }
}

class CreateCustomerHandler(private val customerRepository: CustomerRepository) : CommandHandler<CreateCustomerCommand> {

    override fun handle(command: CreateCustomerCommand) {
        val (name, customerId, country) = command

        if (customerRepository.findByCustomerId(customerId) != null) {
            throw IllegalStateException("Customer with id $customerId already exists")
        }

        val customer = CustomerFactory.create(name, customerId, country)

        customerRepository.save(customer)
    }
}