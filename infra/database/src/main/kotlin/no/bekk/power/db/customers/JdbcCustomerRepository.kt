package no.bekk.power.db.customers

import no.bekk.power.domain.customer.Customer
import no.bekk.power.domain.customer.CustomerEntity
import no.bekk.power.domain.customer.CustomerRepository
import no.bekk.power.domain.meteringpoint.MeteringPointEntity
import no.bekk.power.domain.valuetypes.*
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import java.sql.ResultSet

class JdbcCustomerRepository(private val jdbcTemplate: NamedParameterJdbcTemplate) : CustomerRepository {

    override fun findByCustomerId(id: CustomerId): Customer? {
        val customerEntity = findCustomerEntityById(id)
        val meteringPointEntities = findMeteringPointEntitiesByCustomerId(id)

        return customerEntity?.let {
            Customer(
                customerEntity = customerEntity,
                meteringPoints = meteringPointEntities
                    .associateBy { it.id }
                    .toMutableMap()
            )
        }
    }

    private fun findCustomerEntityById(id: CustomerId): CustomerEntity? =
        jdbcTemplate.query(
            """
                   SELECT * FROM CUSTOMER C 
                   WHERE C.CUSTOMER_ID=:customerId 
                   """.trimIndent(),
            mutableMapOf(
                "customerId" to id.value
            )
        )
        { rs, _ ->
            CustomerEntity(
                name = CustomerName(rs.getString("NAME")),
                customerId = CustomerId(rs.getString("CUSTOMER_ID")),
                country = Country(rs.getString("COUNTRY"))
            )
        }.firstOrNull()

    private fun findMeteringPointEntitiesByCustomerId(id: CustomerId): MutableList<MeteringPointEntity> =
        jdbcTemplate.query(
            """
                   SELECT * FROM METERING_POINT M
                   WHERE M.CUSTOMER_ID=:customerId;
                """.trimIndent(),
            mutableMapOf(
                "customerId" to id.value
            )
        )
        { rs, _ ->
            MeteringPointEntity(
                meteringPointId = MeteringPointId(rs.getString("METERING_POINT_ID")),
                meteringPointName = MeteringPointName(rs.getString("NAME")),
                address = Address(
                    street = rs.getString("STREET"),
                    zip = rs.getString("ZIP")
                ),
                powerZone = PowerZone.valueOf(rs.getString("POWER_ZONE"))
            )
        }

    override fun save(customer: Customer) {
        TODO("Not yet implemented")
    }

    override fun update(customer: Customer) {
        TODO("Not yet implemented")
    }
}
