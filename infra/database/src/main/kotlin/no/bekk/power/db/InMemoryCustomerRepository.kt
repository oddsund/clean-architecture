package no.bekk.power.db

import no.bekk.power.customer.Customer
import no.bekk.power.customer.CustomerRepository
import no.bekk.power.valuetypes.LegalId

class InMemoryCustomerRepository : CustomerRepository {
    private val customers: MutableMap<LegalId, Customer> = mutableMapOf()


    override fun findByLegalId(id: LegalId): Customer? {
        return customers[id]
    }

    override fun save(customer: Customer) {
        customers[customer.getId()] = customer
    }

    override fun update(customer: Customer) {
        TODO("Not yet implemented")
    }
}
