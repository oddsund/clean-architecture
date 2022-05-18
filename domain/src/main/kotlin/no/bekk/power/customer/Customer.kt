package no.bekk.power.customer

import no.bekk.power.meteringpoint.MeteringPointEntity
import no.bekk.power.valuetypes.CustomerId

class Customer internal constructor(
    private val customerEntity: CustomerEntity,
    private val meteringPoints: MutableList<MeteringPointEntity> = mutableListOf()) {

    fun addMeteringPoint(meteringPointEntity: MeteringPointEntity) {
        meteringPoints.add(meteringPointEntity)
    }

    fun removeMeteringPoint(meteringPointEntity: MeteringPointEntity) {
        meteringPoints.remove(meteringPointEntity)
    }

    fun getId(): CustomerId {
        return customerEntity.customerId
    }
}
