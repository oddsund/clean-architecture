package no.bekk.power.domain.consumption

import no.bekk.power.domain.valuetypes.Period

class Consumption(private val consumptionPeriods: List<ConsumptionPeriodEntity>) {

    fun calculateTotalConsumptionForPeriod(period: Period): Double {
        return consumptionPeriods
            .filter { it.period.from >= period.from && it.period.to <= period.to }
            .sumOf { it.value }
    }
}
