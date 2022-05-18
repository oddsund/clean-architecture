package no.bekk.power.customer

import no.bekk.power.entity.CustomerEntity
import no.bekk.power.entity.MeteringPointEntity

class Customer internal constructor(
    private val customerEntity: CustomerEntity,
    private val meteringPoints: MutableList<MeteringPointEntity>) {

    fun addMeteringPoint(meteringPointEntity: MeteringPointEntity) {
        meteringPoints.add(meteringPointEntity)
    }

    fun removeMeteringPoint(meteringPointEntity: MeteringPointEntity) {
        meteringPoints.remove(meteringPointEntity)
    }

    fun getId(): String {
        return customerEntity.ssn
    }
}
