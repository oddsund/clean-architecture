package no.bekk.power.application.implementations

import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import no.bekk.power.domain.customer.Customer
import no.bekk.power.domain.customer.CustomerRepository
import no.bekk.power.domain.valuetypes.Country
import no.bekk.power.domain.valuetypes.CustomerId
import no.bekk.power.domain.valuetypes.CustomerName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomerServiceImplTest {

    private val customerRepository: CustomerRepository = mockk(relaxed = true)
    private val target = CustomerServiceImpl(customerRepository)

    @Test
    fun `createCustomer with new id calls repository`() {
        val name = CustomerName("a")
        val id = CustomerId("42")
        val country = Country("GB")
        every { customerRepository.findByCustomerId(id) } returns null

        target.createCustomer(name, id, country)

        verify(exactly = 1) { customerRepository.save(match{it.id == id}) }
    }

    @Test
    fun `createCustomer with existing id throws`() {
        val name = CustomerName("a")
        val id = CustomerId("42")
        val country = Country("GB")
        every { customerRepository.findByCustomerId(id) } returns Customer(name, id, country)

        assertThrows<IllegalStateException> {
            target.createCustomer(name, id, country)
        }
    }

    @Test
    fun `getCustomer returns entity from repository`() {
        val customer = Customer("a", "b", "c")
        every { customerRepository.findByCustomerId(customer.id) } returns customer

        val result = target.getCustomer(customer.id)

        result shouldBe customer
    }

    @Test
    fun `getAllCustomers returns entities from repository`() {
        TODO("Not implemented")
        /*
        val customers = setOf(Customer("a", "b", "c"))
        every { customerRepository.getAll() } returns customers

        val result = target.getAllCustomers()

        result shouldBe customers
        */
    }
}