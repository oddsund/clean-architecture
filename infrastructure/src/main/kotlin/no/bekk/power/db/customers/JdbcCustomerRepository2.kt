package no.bekk.power.db.customers

import no.bekk.power.domain.customer.Customer
import no.bekk.power.domain.customer.CustomerEntity
import no.bekk.power.domain.customer.CustomerRepository
import no.bekk.power.domain.meteringpoint.MeteringPointEntity
import no.bekk.power.domain.valuetypes.*
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.transaction.annotation.Transactional

open class JdbcCustomerRepository2(private val jdbcTemplate: NamedParameterJdbcTemplate) : CustomerRepository {

    override fun findByCustomerId(id: CustomerId): Customer? {
        val meteringPointsById = mutableMapOf<MeteringPointId, MeteringPointEntity>()
        val customerEntity = jdbcTemplate.query(
            "SELECT * FROM CUSTOMER C " +
                    "LEFT OUTER JOIN METERING_POINT M ON C.CUSTOMER_ID = M.CUSTOMER_ID " +
                    "WHERE C.CUSTOMER_ID = :customerId",
            mutableMapOf(
                "customerId" to id.value
            )
        )
        { rs, _ ->
            rs.getString("METERING_POINT_ID")?.let { id ->
                meteringPointsById.putIfAbsent(
                    MeteringPointId(id),
                    MeteringPointEntity(
                        meteringPointId = MeteringPointId(id),
                        name = MeteringPointName(rs.getString("NAME")),
                        address = Address(
                            street = rs.getString("STREET"),
                            zip = rs.getString("ZIP")
                        ),
                        powerZone = PowerZone.valueOf(rs.getString("POWER_ZONE"))
                    )
                )
            }
            CustomerEntity(
                name = CustomerName(rs.getString("NAME")),
                customerId = CustomerId(rs.getString("CUSTOMER_ID")),
                country = Country(rs.getString("COUNTRY"))
            )
        }.firstOrNull()

        return customerEntity?.let {
            Customer(
                customerEntity = customerEntity,
                meteringPoints = meteringPointsById.values.toList()
            )
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
