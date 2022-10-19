package no.bekk.power.application.consumption.handlers

import no.bekk.power.application.QueryHandler
import no.bekk.power.application.consumption.queries.GetTotalConsumptionForPeriodQuery
import no.bekk.power.application.consumption.service.ConsumptionService
import no.bekk.power.domain.customer.CustomerRepository
import no.bekk.power.domain.valuetypes.Period

class GetTotalConsumptionForPeriodHandler(
    private val customerRepository: CustomerRepository,
    private val consumptionService: ConsumptionService
) : QueryHandler<GetTotalConsumptionForPeriodQuery, Double> {

    override fun handle(query: GetTotalConsumptionForPeriodQuery): Double {
        val customer = customerRepository.findByCustomerId(query.customerId)
            ?: throw IllegalArgumentException("Customer with id ${query.customerId} not found")

        val meteringPoint = customer.findMeteringPoint(query.meteringPointId)
            ?: throw IllegalArgumentException("Metering point with id ${query.meteringPointId} not found")

        val consumption = consumptionService.getConsumptionForMeteringPoint(meteringPoint.id)

        val period = Period(query.from, query.to)
        return consumption.calculateTotalConsumptionForPeriod(period)
    }
}
