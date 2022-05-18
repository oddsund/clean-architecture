package no.bekk.power.entity

import no.bekk.power.valuetypes.Country
import no.bekk.power.valuetypes.CustomerName
import no.bekk.power.valuetypes.LegalId

internal class CustomerEntity(
    internal val name: CustomerName,
    internal val legalId: LegalId,
    internal val country: Country
)
