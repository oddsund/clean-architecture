package no.bekk.power.application.customer

import no.bekk.power.domain.customer.Customer
import no.bekk.power.domain.customer.CustomerRepository
import no.bekk.power.domain.valuetypes.CustomerId
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class CreateCustomerUseCaseTest {

    private val customerRepository = InMemoryCustomerRepository()
    private val createCustomerUseCase = CreateCustomerUseCase(customerRepository)

    @Test
    internal fun given_valid_customer_customer_is_created() {
        val (success, customerId, _) = createCustomerUseCase.create(
            name = "Test Testesen",
            customerId = "12345678",
            country = "Norge"
        )

        assertThat(success).isTrue
        assertThat(customerId.value).isEqualTo("12345678")

        assertThat(customerRepository.findByCustomerId(customerId)).isNotNull
    }

    @Test
    internal fun given_valid_customer_that_already_exists_customer_is_not_created() {
        val (_, _, _) = createCustomerUseCase.create(
            name = "Test Testesen",
            customerId = "12345678",
            country = "Norge"
        )

        val (success, _, message) = createCustomerUseCase.create(
            name = "Test Testesen",
            customerId = "12345678",
            country = "Norge"
        )

        assertThat(success).isFalse
        assertThat(message).isEqualTo("Customer with id 12345678 already exists")
    }

    internal class InMemoryCustomerRepository : CustomerRepository {
        private val customers: MutableMap<CustomerId, Customer> = mutableMapOf()

        override fun findByCustomerId(id: CustomerId): Customer? {
            return customers[id]
        }

        override fun save(customer: Customer) {
            customers[customer.id] = customer
        }

        override fun update(customer: Customer) {
            customers[customer.id] = customer
        }
    }
}
