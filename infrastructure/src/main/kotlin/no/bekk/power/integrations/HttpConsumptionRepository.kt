package no.bekk.power.integrations

import no.bekk.power.domain.consumption.Consumption
import no.bekk.power.domain.consumption.ConsumptionPeriodEntity
import no.bekk.power.domain.consumption.ConsumptionRepository
import no.bekk.power.domain.valuetypes.MeteringPointId
import no.bekk.power.domain.valuetypes.Period
import no.bekk.power.domain.valuetypes.UnitOfMeasurement
import java.time.OffsetDateTime

class HttpConsumptionRepository(private val httpClient: HttpClient) : ConsumptionRepository {

    override fun findByMeteringPointId(meteringPointId: MeteringPointId): Consumption {
        val body = httpClient.meterValues<MeterValuesDTO>()
        val meteringPoint = body!!.meteringpoint

        return Consumption(meteringPoint.metervalue.timeSeries.map {
            ConsumptionPeriodEntity(
                period = Period(
                    from = it.startTime,
                    to = it.endTime
                ),
                value = it.value,
                uom = UnitOfMeasurement.valueOf(it.uom)
            )
        })
    }
}

data class MeterValuesDTO(val meteringpoint: MeteringsPoint) {
    data class MeteringsPoint(
        val meteringPointId: String,
        val metervalue: MeterValue
    )

    data class MeterValue(
        val fromHour: OffsetDateTime,
        val toHour: OffsetDateTime,
        val timeSeries: List<TimeSerie>
    )

    data class TimeSerie(
        val startTime: OffsetDateTime,
        val endTime: OffsetDateTime,
        val value: Double,
        val uom: String
    )
}

