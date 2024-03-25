package com.jsh.rocco;

import com.jsh.rocco.domains.entities.Address;
import com.jsh.rocco.domains.entities.Customer;
import com.jsh.rocco.services.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@SpringBootTest
class RoccoApplicationTests {
    @Autowired
    private CustomerService customerService;

    @Test
    @Transactional
    @Commit
    void contextLoads() {
        Address address = new Address();
        address.setRegion("대구");
        address.setStreet1("테스트1");
        address.setStreet2("테스트2");
        address.setZipCode("11111");
        customerService.addAddress(address);

        Customer customer = new Customer();
        customer.setPhone("01049333657");
        customer.setBirthDate(new Date());
        customer.setFirstName("테스트1");
        customer.setLastName("테스트1");
        customer.setAddress(address);
        customerService.addCustomer(customer);
    }

}
