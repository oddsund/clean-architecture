package no.bekk.power.api.customer.dto

data class CreateCustomerRequest(val name: String, val customerId: String, val legalCountry: String)
