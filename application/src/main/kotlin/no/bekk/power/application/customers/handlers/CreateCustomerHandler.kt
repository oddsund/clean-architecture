package no.bekk.power.application.customers.handlers

import no.bekk.power.application.CommandHandler
import no.bekk.power.application.customers.commands.CreateCustomerCommand
import no.bekk.power.domain.customer.CustomerFactory
import no.bekk.power.domain.customer.CustomerRepository

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
