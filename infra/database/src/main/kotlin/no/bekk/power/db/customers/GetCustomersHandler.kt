package no.bekk.power.db.customers

import no.bekk.power.application.QueryHandler
import no.bekk.power.domain.customer.Customer
import no.bekk.power.domain.customer.CustomerRepository
import no.bekk.power.domain.valuetypes.CustomerId

data class GetCustomer(val customerId: CustomerId) {

    companion object {
        fun with(customerId: String): GetCustomer {
            return GetCustomer(
                customerId = CustomerId(customerId)
            )
        }
    }
}

class GetCustomersHandler(private val customerRepository: CustomerRepository) : QueryHandler<GetCustomer, Customer?> {
    override fun run(query: GetCustomer): Customer? {
        return customerRepository.findByCustomerId(query.customerId)
    }
}