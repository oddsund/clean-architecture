package no.bekk.power.config

import no.bekk.power.db.InMemoryCustomerRepository
import no.bekk.power.domain.customer.CustomerRepository
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
    fun createCustomerUseCase(customerRepository: CustomerRepository): CreateCustomerUseCase {
        return CreateCustomerUseCase(customerRepository)
    }

}
