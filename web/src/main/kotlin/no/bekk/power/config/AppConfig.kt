package no.bekk.power.config

import no.bekk.power.application.customers.handlers.CreateCustomerHandler
import no.bekk.power.application.customers.handlers.GetCustomersHandler
import no.bekk.power.db.InMemoryCustomerRepository
import no.bekk.power.domain.customer.CustomerRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AppConfig {

    @Bean
    fun customerRepository(): CustomerRepository {
        return InMemoryCustomerRepository()
    }

    @Bean
    fun createCustomerHandler(customerRepository: CustomerRepository): CreateCustomerHandler {
        return CreateCustomerHandler(customerRepository)
    }

    @Bean
    fun getCustomerHandler(customerRepository: CustomerRepository): GetCustomersHandler {
        return GetCustomersHandler(customerRepository)
    }

}
