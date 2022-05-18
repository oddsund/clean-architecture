package no.bekk.power.customer

interface CustomerRepository {

    fun findByLegalId(id: String): Customer?

    fun save(customer: Customer): String

    fun update(customer: Customer): String

}
