package no.bekk.power.customer

import no.bekk.power.valuetypes.LegalId

interface CustomerRepository {

    fun findByLegalId(id: LegalId): Customer?

    fun save(customer: Customer)

    fun update(customer: Customer)

}
