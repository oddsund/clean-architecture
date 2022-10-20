package no.bekk.power.domain.consumption

import no.bekk.power.domain.valuetypes.MeteringPointId

interface ConsumptionRepository {

    fun findByMeteringPointId(meteringPointId: MeteringPointId): Consumption

}
