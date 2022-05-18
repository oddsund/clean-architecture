package no.bekk.power.db

import no.bekk.power.customer.Customer
import no.bekk.power.customer.CustomerRepository
import no.bekk.power.valuetypes.CustomerId

class InMemoryCustomerRepository : CustomerRepository {

    private val customers: MutableMap<CustomerId, Customer> = mutableMapOf()

    override fun findByLegalId(id: CustomerId): Customer? {
        return customers[id]
    }

    override fun save(customer: Customer) {
        customers[customer.getId()] = customer
    }

    override fun update(customer: Customer) {
        TODO("Not yet implemented")
    }
}
