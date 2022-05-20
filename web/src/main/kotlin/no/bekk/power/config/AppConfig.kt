package no.bekk.power.config

import com.fasterxml.jackson.databind.ObjectMapper
import no.bekk.power.customer.CustomerRepository
import no.bekk.power.db.InMemoryCustomerRepository
import no.bekk.power.integration.InMemoryMeterValuesService
import no.bekk.power.usecase.consumption.GetConsumptionForPeriodUseCase
import no.bekk.power.usecase.consumption.service.MeterValuesService
import no.bekk.power.usecase.customer.CreateCustomerUseCase
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AppConfig {

    @Bean
    fun customerRepository(): CustomerRepository {
        return InMemoryCustomerRepository()
    }

    @Bean
    fun meterValueService(objectMapper: ObjectMapper): MeterValuesService {
        return InMemoryMeterValuesService(objectMapper)
    }

    @Bean
    fun createCustomerUseCase(customerRepository: CustomerRepository): CreateCustomerUseCase {
        return CreateCustomerUseCase(customerRepository)
    }

    @Bean
    fun getConsumptionForPeriodUseCase(
        customerRepository: CustomerRepository,
        meterValuesService: MeterValuesService): GetConsumptionForPeriodUseCase {
        return GetConsumptionForPeriodUseCase(customerRepository, meterValuesService)
    }
}
