package no.bekk.power.db

import no.bekk.power.customer.Customer
import no.bekk.power.customer.CustomerRepository

class InMemoryCustomerRepository : CustomerRepository {
    private val customers: MutableMap<String, Customer> = mutableMapOf()


    override fun findByLegalId(id: String): Customer? {
        return customers[id]
    }

    override fun save(customer: Customer): String {
        customers.put(customer.getId(), customer)
        return customer.getId()
    }

    override fun update(customer: Customer): String {
        TODO("Not yet implemented")
    }
}
