package no.bekk.power.consumption

import no.bekk.power.valuetypes.Period
import no.bekk.power.valuetypes.UnitOfMeasurement

class ConsumptionTimeSeriesEntity constructor(
    val period: Period,
    val value: Double,
    val uom: UnitOfMeasurement,
) {

}
