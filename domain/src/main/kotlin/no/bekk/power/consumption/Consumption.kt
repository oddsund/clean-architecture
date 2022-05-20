package no.bekk.power.consumption

import no.bekk.power.valuetypes.MeteringPointId
import no.bekk.power.valuetypes.Period

class Consumption(val meteringPointId: MeteringPointId, val period: Period, val consumptionTimeSeriesEntities: List<ConsumptionTimeSeriesEntity>) {

    val totalConsumption: Double
        get() = consumptionTimeSeriesEntities.sumOf { it.value }

}
