package no.bekk.power.config

import no.bekk.power.db.InMemoryCustomerRepository
import no.bekk.power.usecase.customer.CreateCustomerUseCase
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AppConfig {

    @Bean
    fun createCustomerUseCase(): CreateCustomerUseCase {
        return CreateCustomerUseCase(InMemoryCustomerRepository())
    }
}
