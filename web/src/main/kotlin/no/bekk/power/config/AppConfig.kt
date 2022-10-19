package no.bekk.power.config

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import no.bekk.power.application.consumption.handlers.GetTotalConsumptionForPeriodHandler
import no.bekk.power.application.consumption.service.ConsumptionService
import no.bekk.power.application.consumption.service.MeterValuesService
import no.bekk.power.application.customers.handlers.CreateCustomerHandler
import no.bekk.power.application.customers.handlers.GetCustomersHandler
import no.bekk.power.db.customers.JdbcCustomerRepository
import no.bekk.power.domain.customer.CustomerRepository
import no.bekk.power.integrations.HttpClient
import no.bekk.power.integrations.HttpMeterValuesService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate

@Configuration
class AppConfig {

    @Bean
    fun objectMaper(): ObjectMapper {
        return ObjectMapper().registerModule(JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
    }

    @Bean
    fun customerRepository(jdbcTemplate: NamedParameterJdbcTemplate): CustomerRepository {
        return JdbcCustomerRepository(jdbcTemplate)
    }

    @Bean
    fun createCustomerHandler(customerRepository: CustomerRepository): CreateCustomerHandler {
        return CreateCustomerHandler(customerRepository)
    }

    @Bean
    fun customerHandler(customerRepository: CustomerRepository): GetCustomersHandler {
        return GetCustomersHandler(customerRepository)
    }

    @Bean
    fun meterValuesService(): MeterValuesService {
        return HttpMeterValuesService(HttpClient())
    }

    @Bean
    fun consumptionService(meterValuesService: MeterValuesService): ConsumptionService {
        return ConsumptionService(meterValuesService)
    }

    @Bean
    fun totalConsumptionHandler(
        customerRepository: CustomerRepository,
        consumptionService: ConsumptionService
    ): GetTotalConsumptionForPeriodHandler {
        return GetTotalConsumptionForPeriodHandler(customerRepository, consumptionService)
    }

}
