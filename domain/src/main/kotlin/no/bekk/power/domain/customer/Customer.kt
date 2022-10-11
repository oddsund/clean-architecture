package no.bekk.power.domain.customer

import no.bekk.power.domain.meteringpoint.MeteringPointEntity
import no.bekk.power.domain.valuetypes.*

class Customer constructor(
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
