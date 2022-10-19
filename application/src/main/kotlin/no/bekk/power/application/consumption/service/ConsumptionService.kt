package no.bekk.power.application.consumption.service

import no.bekk.power.domain.consumption.Consumption
import no.bekk.power.domain.consumption.ConsumptionPeriodEntity
import no.bekk.power.domain.valuetypes.MeteringPointId
import no.bekk.power.domain.valuetypes.UnitOfMeasurement

class ConsumptionService(private val meterValuesService: MeterValuesService) {

    fun getConsumptionForMeteringPoint(id: MeteringPointId): Consumption {
        val meterValues = meterValuesService.getMeterValuesForPeriod(id)

        return Consumption(meterValues.timeSeries.map {
            ConsumptionPeriodEntity(
                period = it.period,
                value = it.value,
                uom = UnitOfMeasurement.valueOf(it.uom)
            )
        })
    }
}
