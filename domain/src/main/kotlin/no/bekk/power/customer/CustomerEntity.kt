package no.bekk.power.customer

import no.bekk.power.valuetypes.Country
import no.bekk.power.valuetypes.CustomerName
import no.bekk.power.valuetypes.CustomerId

internal class CustomerEntity(
    internal val name: CustomerName,
    internal val customerId: CustomerId,
    internal val country: Country
)
