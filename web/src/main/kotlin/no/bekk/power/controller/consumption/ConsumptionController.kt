package no.bekk.power.controller.consumption

import no.bekk.power.application.QueryHandler
import no.bekk.power.application.consumption.queries.GetTotalConsumptionForPeriodQuery
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.OffsetDateTime

@RestController
class ConsumptionController(private val getTotalConsumptionForPeriodQuery: QueryHandler<GetTotalConsumptionForPeriodQuery, Double>) {

    @GetMapping("/customers/{customerId}/meteringpoints/{meteringPointId}/consumptions")
    fun getTotalConsumptionForPeriod(
        @PathVariable("customerId") customerId: String,
        @PathVariable("meteringPointId") meteringPointId: String,
        @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) from: OffsetDateTime,
        @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) to: OffsetDateTime): ResponseEntity<Any> {
        val totalConsumption = getTotalConsumptionForPeriodQuery.handle(
            GetTotalConsumptionForPeriodQuery.with(
                customerId = customerId,
                meteringPointId = meteringPointId,
                from = from,
                to = to
            )
        )
        return ResponseEntity.ok(object {
            val totalConsumption = totalConsumption
        })
    }
}
