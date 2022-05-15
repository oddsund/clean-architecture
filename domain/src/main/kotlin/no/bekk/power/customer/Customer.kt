package no.bekk.power.customer

import no.bekk.power.entity.CustomerEntity
import no.bekk.power.entity.MeteringPointEntity

class Customer(val customerEntity: CustomerEntity, val meteringPoints: MutableList<MeteringPointEntity>) {

    fun addMeteringPoint(meteringPointEntity: MeteringPointEntity) {
        meteringPoints.add(meteringPointEntity)
    }

    fun removeMeteringPoint(meteringPointEntity: MeteringPointEntity) {
        meteringPoints.remove(meteringPointEntity)
    }
}
