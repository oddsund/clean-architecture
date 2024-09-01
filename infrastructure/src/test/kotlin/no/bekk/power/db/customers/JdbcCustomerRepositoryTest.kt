package no.bekk.power.db.customers

import io.kotest.matchers.ints.exactly
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import no.bekk.power.domain.customer.Customer
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JdbcCustomerRepositoryTest {

    private val jdbcTemplate: NamedParameterJdbcTemplate = mockk(relaxed = true)
    private val target = JdbcCustomerRepository(jdbcTemplate)

    @Test
    fun `findByCustomerId with id selects from db and returns entity`() {
        val customer = Customer("name", "id", "country")
        every {
            jdbcTemplate.query(
                match({ it.startsWith("SELECT * FROM CUSTOMER C WHERE C.CUSTOMER_ID = ") } ),
                any<Map<String,String>>(),
                any<RowMapper<Customer>>())
        } returns listOf(customer)

        val result = target.findByCustomerId(customer.id)

        result shouldBe customer
    }

    @Test
    fun `getAll returns all entites from db`() {
        TODO("Not implemented")
        /*
        val expected = listOf(
            Customer("abc", "17", "land"),
            Customer("def", "42", "land"))
        every {
            jdbcTemplate.query(
                "SELECT * FROM CUSTOMER",
                any<RowMapper<Customer>>())
        } returns expected

        val result = target.getAll()

        result shouldBe expected
        */
    }

    @Test
    fun `save calls db with insert query`() {
        val customer = Customer("xxx", "yyy", "zzz")

        target.save(customer)

        verify(exactly = 1) { jdbcTemplate.update(
            match { it.startsWith("INSERT INTO CUSTOMER (CUSTOMER_ID, NAME, COUNTRY) VALUES") } ,
            match<MutableMap<String, String>> { it["customerId"] == customer.id.value }
        )}
    }

    @Test
    fun `update calls db with update query`() {
        val customer = Customer("xxx", "yyy", "zzz")

        target.update(customer)

        verify(exactly = 1) { jdbcTemplate.update(
            match { it.startsWith("UPDATE CUSTOMER") } ,
            match<MutableMap<String, String>> { it["customerId"] == customer.id.value }
        )}
    }
}