package org.example.repos;

import org.example.entity.CustomerEntity;
import org.springframework.data.repository.ListCrudRepository;

public interface CustomerRepo extends ListCrudRepository<CustomerEntity, Long> {
    CustomerEntity findByLogin(String login);
//    User findByUsername(String username);

}
