package no.bekk.power.domain.consumption

import no.bekk.power.domain.valuetypes.MeteringPointId
import no.bekk.power.domain.valuetypes.Period

class Consumption(val meteringPointId: MeteringPointId, val period: Period, val consumptionPeriodEntity: List<ConsumptionPeriodEntity>) {

    val totalConsumption: Double
        get() = consumptionPeriodEntity.sumOf { it.value }

}
