package no.bekk.power.application.implementations

import no.bekk.power.application.CustomerService
import no.bekk.power.domain.customer.Customer
import no.bekk.power.domain.customer.CustomerRepository
import no.bekk.power.domain.valuetypes.Country
import no.bekk.power.domain.valuetypes.CustomerId
import no.bekk.power.domain.valuetypes.CustomerName

class CustomerServiceImpl(private val customerRepository: CustomerRepository) :
    CustomerService {

    override fun createCustomer(name: CustomerName, customerId: CustomerId, country: Country) {
        if (customerRepository.findByCustomerId(customerId) != null) {
            throw IllegalStateException("Customer with id $customerId already exists")
        }

        val customer = Customer(name, customerId, country)

        customerRepository.save(customer)
    }

    override fun getCustomer(customerId: CustomerId): Customer? {
        return customerRepository.findByCustomerId(customerId)
    }
}