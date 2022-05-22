package no.bekk.power.consumption

import no.bekk.power.valuetypes.MeteringPointId
import no.bekk.power.valuetypes.Period

class Consumption(val meteringPointId: MeteringPointId, val period: Period, val consumptionPeriodEntity: List<ConsumptionPeriodEntity>) {

    val totalConsumption: Double
        get() = consumptionPeriodEntity.sumOf { it.value }

}
