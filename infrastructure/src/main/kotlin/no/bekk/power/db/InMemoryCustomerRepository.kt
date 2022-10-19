package no.bekk.power.db

import no.bekk.power.domain.customer.Customer
import no.bekk.power.domain.customer.CustomerRepository
import no.bekk.power.domain.valuetypes.CustomerId

class InMemoryCustomerRepository : CustomerRepository {

    private val customers: MutableMap<CustomerId, Customer> = mutableMapOf()

    override fun findByCustomerId(id: CustomerId): Customer? {
        return customers[id]
    }

    override fun save(customer: Customer) {
        customers[customer.id] = customer
    }

    override fun update(customer: Customer) {
        customers[customer.id] = customer
    }
}
