package no.bekk.power.consumption

import no.bekk.power.valuetypes.Period
import no.bekk.power.valuetypes.UnitOfMeasurement

class ConsumptionPeriodEntity constructor(
    internal val period: Period,
    internal val value: Double,
    internal val uom: UnitOfMeasurement,
) {

}
