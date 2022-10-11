package no.bekk.power.domain.customer

import no.bekk.power.domain.valuetypes.Country
import no.bekk.power.domain.valuetypes.CustomerName
import no.bekk.power.domain.valuetypes.CustomerId

class CustomerEntity(
    internal val name: CustomerName,
    internal val customerId: CustomerId,
    internal val country: Country
)
