package no.bekk.power.controller.customer.dto

import no.bekk.power.domain.customer.Customer

data class GetCustomerResponse(val customerId: String, val name: String, val country: String) {

    companion object {
        fun from(customer: Customer): GetCustomerResponse {
            return GetCustomerResponse(
                customerId = customer.id.value,
                name = customer.name.value,
                country = customer.country.value
            )
        }
    }
}
