package no.bekk.power.customer

import no.bekk.power.entity.CustomerEntity
import no.bekk.power.valuetypes.Country
import no.bekk.power.valuetypes.CustomerName
import no.bekk.power.valuetypes.LegalId

class CustomerFactory {

    companion object {

        fun create(name: String, legalId: String, country: String): Customer {
            return Customer(
                customerEntity = CustomerEntity(
                    name = CustomerName(name),
                    legalId = LegalId(legalId),
                    country = Country(country)
                ),
                mutableListOf()
            )
        }
    }
}
