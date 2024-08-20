package no.bekk.power.controller.customer

import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import no.bekk.power.application.CustomerService
import no.bekk.power.application.MeteringPointService
import no.bekk.power.controller.customer.dto.CreateCustomerRequest
import no.bekk.power.controller.customer.dto.GetCustomerResponse
import no.bekk.power.domain.customer.Customer
import no.bekk.power.domain.valuetypes.Country
import no.bekk.power.domain.valuetypes.CustomerId
import no.bekk.power.domain.valuetypes.CustomerName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.net.URI

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomersControllerTest {

    private val customerService: CustomerService = mockk()
    private val meteringPointService: MeteringPointService = mockk()
    private val target = CustomersController(customerService, meteringPointService)

    @Test
    fun `getCustomer with id returns customer`() {
        val id = CustomerId("some id")
        val customer = Customer(CustomerName("abc"), id, Country("SE"))
        every { customerService.getCustomer(id) } returns customer

        val result = target.getCustomer(id.value)

        result shouldBe ResponseEntity.ok(GetCustomerResponse.from(customer))
    }

    @Test
    fun `getCustomer with unknown id returns NotFound`() {
        val id = CustomerId("some id")
        every { customerService.getCustomer(id) } returns null

        val result = target.getCustomer(id.value)

        result shouldBe ResponseEntity.notFound().build()
    }

    @Test
    fun `getCustomers returns all customers from service`(){
        val customers = setOf(
            Customer(CustomerName("abc"), CustomerId("1"), Country("SE")),
            Customer(CustomerName("def"), CustomerId("2"), Country("FI")),
        )
        every { customerService.getAllCustomers() } returns customers

        val result = target.getAllCustomers()

        result shouldBe ResponseEntity.ok(customers.map { GetCustomerResponse.from(it) })
    }

    @Test
    fun `createCustomer with invalid data returns server error`(){
        val request = CreateCustomerRequest("", "", "")
        val errorMessage = "invalid data"
        every { customerService.createCustomer(any(), any(), any()) } throws Exception(errorMessage)

        val result = target.createCustomer(request)

        result shouldBe ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage)
    }

    @Test
    fun `createCustomer with data calls service and returns created status`(){
        val request = CreateCustomerRequest("a", "b", "c")
        every { customerService.createCustomer(CustomerName(request.name), CustomerId(request.customerId), Country(request.country)) } returns Unit

        val result = target.createCustomer(request)

        result shouldBe ResponseEntity.created(URI.create("/customer/b")).build()
    }
}