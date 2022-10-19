package no.bekk.power.application.consumption.queries

import no.bekk.power.domain.valuetypes.CustomerId
import no.bekk.power.domain.valuetypes.MeteringPointId
import java.time.OffsetDateTime

class GetTotalConsumptionForPeriodQuery(
    val customerId: CustomerId,
    val meteringPointId: MeteringPointId,
    val from: OffsetDateTime,
    val to: OffsetDateTime
) {

    companion object {
        fun with(customerId: String, meteringPointId: String, from: OffsetDateTime, to: OffsetDateTime): GetTotalConsumptionForPeriodQuery {
            return GetTotalConsumptionForPeriodQuery(
                customerId = CustomerId(customerId),
                meteringPointId = MeteringPointId(meteringPointId),
                from = from,
                to = to
            )
        }
    }

}
