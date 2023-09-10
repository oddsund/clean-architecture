package no.bekk.power.application

import no.bekk.power.domain.customer.Customer
import no.bekk.power.domain.valuetypes.Country
import no.bekk.power.domain.valuetypes.CustomerId
import no.bekk.power.domain.valuetypes.CustomerName

interface CustomerService {
    fun createCustomer(name: CustomerName, customerId: CustomerId, country: Country)
    fun getCustomer(customerId: CustomerId): Customer?
}