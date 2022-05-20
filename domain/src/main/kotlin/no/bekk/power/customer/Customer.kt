package no.bekk.power.customer

import no.bekk.power.meteringpoint.MeteringPointEntity
import no.bekk.power.valuetypes.*

class Customer internal constructor(
    private val customerEntity: CustomerEntity,
    private val meteringPoints: MutableMap<MeteringPointId, MeteringPointEntity> = mutableMapOf()
) {

    fun findMeteringPoint(meteringPointId: MeteringPointId): MeteringPointEntity? {
        return meteringPoints[meteringPointId]
    }

    fun addMeteringPoint(meteringPointEntity: MeteringPointEntity) {
        meteringPoints[meteringPointEntity.meteringPointId] = meteringPointEntity
    }

    fun removeMeteringPoint(meteringPointId: MeteringPointId) {
        meteringPoints.remove(meteringPointId)
    }

    val id: CustomerId
        get() = customerEntity.customerId
}
