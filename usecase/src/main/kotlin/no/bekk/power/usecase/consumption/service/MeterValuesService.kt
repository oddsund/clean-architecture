package no.bekk.power.usecase.consumption.service

import no.bekk.power.valuetypes.MeteringPointId
import no.bekk.power.valuetypes.Period

interface MeterValuesService {

    fun getMeterValues(meteringPointId: MeteringPointId, period: Period): MeterValues
}
