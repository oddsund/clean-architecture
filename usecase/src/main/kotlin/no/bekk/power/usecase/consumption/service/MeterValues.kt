
package no.bekk.power.usecase.consumption.service

import no.bekk.power.domain.valuetypes.MeteringPointId
import no.bekk.power.domain.valuetypes.Period

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
