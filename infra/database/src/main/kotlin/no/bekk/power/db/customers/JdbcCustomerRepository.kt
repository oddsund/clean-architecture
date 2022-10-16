package no.bekk.power.db.customers

import no.bekk.power.domain.customer.Customer
import no.bekk.power.domain.customer.CustomerEntity
import no.bekk.power.domain.customer.CustomerRepository
import no.bekk.power.domain.meteringpoint.MeteringPointEntity
import no.bekk.power.domain.valuetypes.*
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.transaction.annotation.Transactional

open class JdbcCustomerRepository(private val jdbcTemplate: NamedParameterJdbcTemplate) : CustomerRepository {

    override fun findByCustomerId(id: CustomerId): Customer? {
        val customerEntity = findCustomerEntityById(id)

        return customerEntity?.let {
            Customer(
                customerEntity = customerEntity,
                meteringPointsMap = findMeteringPointEntitiesByCustomerId(id)
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
                name = MeteringPointName(rs.getString("NAME")),
                address = Address(
                    street = rs.getString("STREET"),
                    zip = rs.getString("ZIP")
                ),
                powerZone = PowerZone.valueOf(rs.getString("POWER_ZONE"))
            )
        }

    @Transactional
    override fun save(customer: Customer) {
        saveCustomer(customer)
        saveMeteringPoints(customer.id, customer.meteringPoints)
    }

    @Transactional
    override fun update(customer: Customer) {
        val customerFromDb = findByCustomerId(customer.id)
        val (meteringPointIdsToAdd, meteringPointIdsToUpdate, meteringPointIdsToDelete) = filterMeteringPointIds(
            customerFromDb?.meteringPoints,
            customer.meteringPoints
        )

        updateCustomer(customer)
        saveMeteringPoints(customer.id, customer.meteringPoints.filter { it.id in meteringPointIdsToAdd })
        updateMeteringPoints(customer.meteringPoints.filter { it.id in meteringPointIdsToUpdate })
        deleteMeteringPoints(meteringPointIdsToDelete)
    }

    private fun saveCustomer(customer: Customer) {
        jdbcTemplate.update(
            """
                    INSERT INTO CUSTOMER (CUSTOMER_ID, NAME, COUNTRY)
                    VALUES (:customerId, :name, :country);
                """.trimIndent(),
            mutableMapOf(
                "customerId" to customer.id.value,
                "name" to customer.name.value,
                "country" to customer.country.value
            )
        )
    }

    private fun updateCustomer(customer: Customer) {
        jdbcTemplate.update(
            """
                    UPDATE CUSTOMER 
                    SET name = :name, country = :country
                    WHERE CUSTOMER_ID = :customerId
                """.trimIndent(),
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
                """
                    INSERT INTO METERING_POINT(NAME, METERING_POINT_ID, POWER_ZONE, STREET, ZIP, CUSTOMER_ID)
                    VALUES (:name, :meteringPointId, :powerZone, :street, :zip, :customerId)
            """.trimIndent(),
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

    private fun updateMeteringPoints(meteringPoints: List<MeteringPointEntity>) {
        meteringPoints.forEach { meteringPoint ->
            jdbcTemplate.update(
                """
                    UPDATE METERING_POINT
                    SET NAME = :name, POWER_ZONE = :powerZone, STREET = :street, ZIP = :zip
                    WHERE METERING_POINT_ID = :meteringPointId
            """.trimIndent(),
                mutableMapOf(
                    "name" to meteringPoint.name.value,
                    "powerZone" to meteringPoint.powerZone.name,
                    "street" to meteringPoint.address.street,
                    "zip" to meteringPoint.address.zip,
                    "meteringPointId" to meteringPoint.id.value
                )
            )
        }
    }

    private fun deleteMeteringPoints(meteringPointIds: List<MeteringPointId>) {
        jdbcTemplate.update(
            """
                    DELETE FROM METERING_POINT
                    WHERE METERING_POINT_ID IN (:meteringPointIds)
                """.trimIndent(),
            mutableMapOf(
                "meteringPointIds" to meteringPointIds.map { it.value },
            )
        )
    }

    private fun filterMeteringPointIds(
        meteringPointsFromDb: List<MeteringPointEntity>?,
        meteringPointsFromInput: List<MeteringPointEntity>
    ): Triple<List<MeteringPointId>, List<MeteringPointId>, List<MeteringPointId>> {
        val meteringPointIdsFromDb = meteringPointsFromDb?.map { it.id } ?: emptyList()
        val meteringPointIdsFromInput = meteringPointsFromInput.map { it.id }

        val meteringPointIdsToAdd = meteringPointIdsFromInput.filterNot { it in meteringPointIdsFromDb }
        val meteringPointIdsToUpdate = meteringPointIdsFromDb.filter { it in meteringPointIdsFromInput }
        val meteringPointIdsToDelete = meteringPointIdsFromDb.filterNot { it in meteringPointIdsFromInput }
        return Triple(meteringPointIdsToAdd, meteringPointIdsToUpdate, meteringPointIdsToDelete)
    }
}
