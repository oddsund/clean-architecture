package no.bekk.power.application.customers.commands

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
