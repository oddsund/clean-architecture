package no.bekk.power.controller.consumption

import no.bekk.power.usecase.consumption.GetConsumptionForPeriodUseCase
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.ZonedDateTime

@RestController
class ConsumptionController(private val consumptionForPeriodUseCase: GetConsumptionForPeriodUseCase) {

    @GetMapping("/customer/{customerId}/meteringpoint/{meteringPointId}/consumption")
    fun getTotalConsumptionForPeriod(
        @PathVariable("customerId") customerId: String,
        @PathVariable("meteringPointId") meteringPointId: String,
        @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) from: ZonedDateTime,
        @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) to: ZonedDateTime): ResponseEntity<Any> {
        val totalConsumption = consumptionForPeriodUseCase.getTotalConsumption(customerId, meteringPointId, from, to)
        return ResponseEntity.ok(totalConsumption)
    }
}
