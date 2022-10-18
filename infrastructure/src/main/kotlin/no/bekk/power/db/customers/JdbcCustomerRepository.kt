package no.bekk.power.db.customers

import no.bekk.power.domain.customer.Customer
import no.bekk.power.domain.customer.CustomerRepository
import no.bekk.power.domain.meteringpoint.MeteringPointEntity
import no.bekk.power.domain.valuetypes.*
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.transaction.annotation.Transactional

open class JdbcCustomerRepository(private val jdbcTemplate: NamedParameterJdbcTemplate) : CustomerRepository {

    override fun findByCustomerId(id: CustomerId): Customer? {
        val meteringPointsById = mutableMapOf<String, MeteringPointEntity>()

        val customer = jdbcTemplate.query(
            "SELECT * FROM CUSTOMER C " +
                    "LEFT OUTER JOIN METERING_POINT M ON C.CUSTOMER_ID = M.CUSTOMER_ID " +
                    "WHERE C.CUSTOMER_ID = :customerId",
            mutableMapOf(
                "customerId" to id.value
            )
        )
        { rs, _ ->
            rs.getString("METERING_POINT_ID")?.let { meteringPointId ->
                meteringPointsById.putIfAbsent(
                    meteringPointId,
                    MeteringPointEntity(
                        meteringPointId = meteringPointId,
                        name = rs.getString("NAME"),
                        street = rs.getString("STREET"),
                        zip = rs.getString("ZIP"),
                        powerZone = rs.getString("POWER_ZONE")
                    )
                )
            }
            Customer(
                name = rs.getString("NAME"),
                customerId = rs.getString("CUSTOMER_ID"),
                country = rs.getString("COUNTRY")
            )
        }.firstOrNull()

        return customer?.let {
            meteringPointsById.values.forEach(customer::addMeteringPoint)
            customer
        }
    }

    @Transactional
    override fun save(customer: Customer) {
        saveCustomer(customer)
        saveMeteringPoints(customer.id, customer.meteringPoints)
    }

    @Transactional
    override fun update(customer: Customer) {
        val customerFromDb = findByCustomerId(customer.id)
        customerFromDb?.let {
            updateCustomer(customer)
            deleteMeteringPoints(it.meteringPoints)
            saveMeteringPoints(customer.id, customer.meteringPoints)
        }
    }

    private fun saveCustomer(customer: Customer) {
        jdbcTemplate.update(
            "INSERT INTO CUSTOMER (CUSTOMER_ID, NAME, COUNTRY) VALUES (:customerId, :name, :country)",
            mutableMapOf(
                "customerId" to customer.id.value,
                "name" to customer.name.value,
                "country" to customer.country.value
            )
        )
    }

    private fun updateCustomer(customer: Customer) {
        jdbcTemplate.update(
            "UPDATE CUSTOMER SET name = :name, country = :country  WHERE CUSTOMER_ID = :customerId",
            mutableMapOf(
                "name" to customer.name.value,
                "country" to customer.country.value,
                "customerId" to customer.id.value
            )
        )
    }

    private fun saveMeteringPoints(customerId: CustomerId, meteringPoints: List<MeteringPointEntity>) {
        meteringPoints.forEach { meteringPoint ->
            jdbcTemplate.update(
                "INSERT INTO METERING_POINT(NAME, METERING_POINT_ID, POWER_ZONE, STREET, ZIP, CUSTOMER_ID) " +
                        "VALUES (:name, :meteringPointId, :powerZone, :street, :zip, :customerId)",
                mutableMapOf(
                    "name" to meteringPoint.name.value,
                    "meteringPointId" to meteringPoint.id.value,
                    "powerZone" to meteringPoint.powerZone.name,
                    "street" to meteringPoint.address.street,
                    "zip" to meteringPoint.address.zip,
                    "customerId" to customerId.value
                )
            )
        }
    }

    private fun deleteMeteringPoints(meteringPointIds: List<MeteringPointEntity>) {
        jdbcTemplate.update(
            "DELETE FROM METERING_POINT WHERE METERING_POINT_ID IN (:ids)",
            mutableMapOf(
                "ids" to meteringPointIds.map { m -> m.id.value },
            )
        )
    }
}
