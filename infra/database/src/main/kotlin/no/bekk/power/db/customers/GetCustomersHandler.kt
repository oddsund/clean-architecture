package no.bekk.power.db.customers

import no.bekk.power.application.QueryHandler
import no.bekk.power.domain.customer.Customer
import no.bekk.power.domain.customer.CustomerRepository
import no.bekk.power.domain.valuetypes.CustomerId

data class GetCustomerQuery(val customerId: CustomerId) {

    companion object {
        fun with(customerId: String): GetCustomerQuery {
            return GetCustomerQuery(
                customerId = CustomerId(customerId)
            )
        }
    }
}

class GetCustomersHandler(private val customerRepository: CustomerRepository) : QueryHandler<GetCustomerQuery, Customer?> {
    override fun run(query: GetCustomerQuery): Customer? {
        return customerRepository.findByCustomerId(query.customerId)
    }
}