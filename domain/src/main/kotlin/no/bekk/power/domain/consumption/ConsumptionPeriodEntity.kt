package no.bekk.power.domain.consumption
import no.bekk.power.domain.valuetypes.Period	
import no.bekk.power.domain.valuetypes.UnitOfMeasurement	

class ConsumptionPeriodEntity constructor(	
    internal val period: Period,	
    internal val value: Double,	
    internal val uom: UnitOfMeasurement,	
)
