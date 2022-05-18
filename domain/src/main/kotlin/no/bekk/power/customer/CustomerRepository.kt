package no.bekk.power.customer

import no.bekk.power.valuetypes.CustomerId

interface CustomerRepository {

    fun findByLegalId(id: CustomerId): Customer?

    fun save(customer: Customer)

    fun update(customer: Customer)

}
