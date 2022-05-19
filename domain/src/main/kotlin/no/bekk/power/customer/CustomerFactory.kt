package no.bekk.power.customer

import no.bekk.power.valuetypes.Country
import no.bekk.power.valuetypes.CustomerName
import no.bekk.power.valuetypes.CustomerId

class CustomerFactory {

    companion object {

        fun create(name: String, customerId: String, country: String): Customer {
            return Customer(
                customerEntity = CustomerEntity(
                    name = CustomerName(name),
                    customerId = CustomerId(customerId),
                    country = Country(country)
                )
            )
        }
    }
}
