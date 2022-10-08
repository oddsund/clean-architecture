package no.bekk.power.domain.customer

import no.bekk.power.domain.valuetypes.Country
import no.bekk.power.domain.valuetypes.CustomerName
import no.bekk.power.domain.valuetypes.CustomerId

class CustomerFactory {

    companion object {
        fun create(name: String, customerId: CustomerId, country: Country): Customer {
            return Customer(
                customerEntity = CustomerEntity(
                    name = CustomerName(name),
                    customerId = customerId,
                    country = country
                )
            )
        }
    }
}
