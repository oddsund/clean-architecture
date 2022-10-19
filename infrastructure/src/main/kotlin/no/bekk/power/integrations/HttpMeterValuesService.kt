package no.bekk.power.integrations

import no.bekk.power.application.consumption.service.MeterValues
import no.bekk.power.application.consumption.service.MeterValuesService
import no.bekk.power.domain.valuetypes.MeteringPointId
import no.bekk.power.domain.valuetypes.Period
import java.time.OffsetDateTime


class HttpMeterValuesService(private val httpClient: HttpClient) : MeterValuesService {

    override fun getMeterValuesForPeriod(meteringPointId: MeteringPointId): MeterValues {
        val body = httpClient.meterValues<MeterValuesDTO>()
        val meteringPoint = body!!.meteringpoint

        return MeterValues(
            meteringPointId = MeteringPointId(meteringPoint.meteringPointId),
            period = Period(
                from = meteringPoint.metervalue.fromHour,
                to = meteringPoint.metervalue.toHour,
            ),
            timeSeries = meteringPoint.metervalue.timeSeries
                .map {
                    MeterValues.TimeSeries(
                        period = Period(
                            from = it.startTime,
                            to = it.endTime,
                        ),
                        value = it.value,
                        uom = it.uom
                    )
                }
        )
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
