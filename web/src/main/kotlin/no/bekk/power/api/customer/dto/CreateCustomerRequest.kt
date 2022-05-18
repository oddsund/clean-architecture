package no.bekk.power.api.customer.dto

data class CreateCustomerRequest(val name: String, val legalId: String, val legalCountry: String)
