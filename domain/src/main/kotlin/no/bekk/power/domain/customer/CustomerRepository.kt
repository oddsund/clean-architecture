package no.bekk.power.domain.customer

import no.bekk.power.domain.valuetypes.CustomerId

interface CustomerRepository {

    fun findByCustomerId(id: CustomerId): Customer?

    fun save(customer: Customer)

    fun update(customer: Customer)

}
