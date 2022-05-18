package no.bekk.power.customer

import no.bekk.power.entity.CustomerEntity

class CustomerFactory {

    companion object {

        fun create(name: String, legalId: String, country: String): Customer {
            return Customer(
                customerEntity = CustomerEntity(name, legalId, country),
                mutableListOf()
            )
        }
    }
}
