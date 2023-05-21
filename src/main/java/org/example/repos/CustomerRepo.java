package org.example.repos;

import org.example.entity.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListCrudRepository;

public interface CustomerRepo extends ListCrudRepository<Customer, Long> {
    Customer findByLogin(String login);
//    User findByUsername(String username);

}
