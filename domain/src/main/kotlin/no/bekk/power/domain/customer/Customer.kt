package no.bekk.power.domain.customer

import no.bekk.power.domain.meteringpoint.MeteringPointEntity
import no.bekk.power.domain.valuetypes.*

class Customer private constructor(
    private val customerEntity: CustomerEntity,
    private val meteringPointsMap: MutableMap<MeteringPointId, MeteringPointEntity> = mutableMapOf()
) {

    constructor(
        customerEntity: CustomerEntity,
        meteringPoints: List<MeteringPointEntity> = listOf()
    ) : this(customerEntity, meteringPoints.associateBy { m -> m.id }.toMutableMap())

    fun findMeteringPoint(meteringPointId: MeteringPointId): MeteringPointEntity? {
        return this.meteringPointsMap[meteringPointId]
    }

    fun addMeteringPoint(meteringPointEntity: MeteringPointEntity) {
        this.meteringPointsMap[meteringPointEntity.meteringPointId] = meteringPointEntity
    }

    fun removeMeteringPoint(meteringPointId: MeteringPointId) {
        this.meteringPointsMap.remove(meteringPointId)
    }

    val id: CustomerId
        get() = customerEntity.customerId
    val name: CustomerName
        get() = customerEntity.name
    val country: Country
        get() = customerEntity.country

    val meteringPoints: List<MeteringPointEntity>
        get() = this.meteringPointsMap.values.toList()
}
