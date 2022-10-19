package no.bekk.power.application.consumption.service

import no.bekk.power.domain.valuetypes.MeteringPointId
import no.bekk.power.domain.valuetypes.Period

interface MeterValuesService {

    fun getMeterValuesForPeriod(meteringPointId: MeteringPointId): MeterValues

}

data class MeterValues(
    val meteringPointId: MeteringPointId,
    val period: Period,
    val timeSeries: List<TimeSeries>
) {
    data class TimeSeries(
        val period: Period,
        val value: Double,
        val uom: String
    )
}
