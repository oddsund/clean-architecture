package no.bekk.power.integration

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import no.bekk.power.usecase.consumption.service.MeterValues
import no.bekk.power.usecase.consumption.service.MeterValuesService
import no.bekk.power.valuetypes.MeteringPointId
import no.bekk.power.valuetypes.Period
import java.time.ZonedDateTime

class InMemoryMeterValuesService(objectMapper: ObjectMapper) : MeterValuesService {

    private val meterValuesDTO: MeterValuesDTO =
        objectMapper.readValue(this::class.java.getResourceAsStream("/metervalues.json")!!)

    override fun getMeterValues(meteringPointId: MeteringPointId, period: Period): MeterValues {
        // Just get the first meteringPoint to always return a value for every meteringPointId
        val meteringPoint = meterValuesDTO.meteringpoints[0]

        return MeterValues(
            meteringPointId = MeteringPointId(meteringPoint.meteringPointId),
            period = Period(
                from = meteringPoint.metervalue.fromHour,
                to = meteringPoint.metervalue.toHour,
            ),
            timeSeries = meteringPoint.metervalue.timeSeries
                .filter { it.startTime >= period.from && it.endTime <= period.to }
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

data class MeterValuesDTO(val meteringpoints: List<MeteringsPoints>) {
    data class MeteringsPoints(
        val meteringPointId: String,
        val metervalue: MeterValue
    )

    data class MeterValue(
        val fromHour: ZonedDateTime,
        val toHour: ZonedDateTime,
        val timeSeries: List<TimeSerie>
    )

    data class TimeSerie(
        val startTime: ZonedDateTime,
        val endTime: ZonedDateTime,
        val value: Double,
        val uom: String
    )
}
