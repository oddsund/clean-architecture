package no.bekk.power.application.customers.handlers

import no.bekk.power.application.QueryHandler
import no.bekk.power.application.customers.queries.GetCustomerQuery
import no.bekk.power.domain.customer.Customer
import no.bekk.power.domain.customer.CustomerRepository

class GetCustomersHandler(private val customerRepository: CustomerRepository) : QueryHandler<GetCustomerQuery, Customer?> {
    override fun handle(query: GetCustomerQuery): Customer? {
        return customerRepository.findByCustomerId(query.customerId)
    }
}
