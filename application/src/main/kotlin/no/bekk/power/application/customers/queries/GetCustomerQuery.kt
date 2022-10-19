package no.bekk.power.application.customers.queries

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
