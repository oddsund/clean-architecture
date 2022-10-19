package no.bekk.power.db.customers

import no.bekk.power.domain.customer.Customer
import no.bekk.power.domain.customer.CustomerRepository
import no.bekk.power.domain.meteringpoint.MeteringPointEntity
import no.bekk.power.domain.valuetypes.*
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.transaction.annotation.Transactional

open class JdbcCustomerRepository(private val jdbcTemplate: NamedParameterJdbcTemplate) : CustomerRepository {

    override fun findByCustomerId(id: CustomerId): Customer? {
        return jdbcTemplate.query(
            "SELECT * FROM CUSTOMER C WHERE C.CUSTOMER_ID = :customerId",
            mutableMapOf(
                "customerId" to id.value
            )
        )
        { rs, _ ->
            Customer(
                name = rs.getString("NAME"),
                customerId = rs.getString("CUSTOMER_ID"),
                country = rs.getString("COUNTRY")
            )
        }.firstOrNull()
    }

    @Transactional
    override fun save(customer: Customer) {
        saveCustomerEntity(customer)
    }

    @Transactional
    override fun update(customer: Customer) {
        updateCustomerEntity(customer)
    }

    private fun saveCustomerEntity(customer: Customer) {
        jdbcTemplate.update(
            "INSERT INTO CUSTOMER (CUSTOMER_ID, NAME, COUNTRY) VALUES (:customerId, :name, :country)",
            mutableMapOf(
                "customerId" to customer.id.value,
                "name" to customer.name.value,
                "country" to customer.country.value
            )
        )
    }

    private fun updateCustomerEntity(customer: Customer) {
        jdbcTemplate.update(
            "UPDATE CUSTOMER SET name = :name, country = :country  WHERE CUSTOMER_ID = :customerId",
            mutableMapOf(
                "name" to customer.name.value,
                "country" to customer.country.value,
                "customerId" to customer.id.value
            )
        )
    }
}
