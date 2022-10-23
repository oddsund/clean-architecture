package no.bekk.power.domain.customer

import no.bekk.power.domain.valuetypes.Country
import no.bekk.power.domain.valuetypes.CustomerId
import no.bekk.power.domain.valuetypes.CustomerName

class Customer constructor(
    private val customerEntity: CustomerEntity
) {
    constructor(name: CustomerName, customerId: CustomerId, country: Country) :
            this(CustomerEntity(name, customerId, country))

    constructor(name: String, customerId: String, country: String) :
            this(CustomerEntity(name, customerId, country))

    val id: CustomerId
        get() = customerEntity.customerId
    val name: CustomerName
        get() = customerEntity.name
    val country: Country
        get() = customerEntity.country
}
