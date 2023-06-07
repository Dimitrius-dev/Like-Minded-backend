package org.example.service;

import org.example.entity.CustomerEntity;
import org.example.exception.UserAlreadyExistsException;
import org.example.exception.UserNotFoundException;
import org.example.jwt.JwtProvider;
import org.example.model.AuthModel;
import org.example.repos.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    public AuthService() {}
    private CustomerRepo customerRepo;
    private JwtProvider jwtProvider;

    private BCryptPasswordEncoder passwordHashEncoder;

    @Autowired
    public AuthService(CustomerRepo userRepo,
                       BCryptPasswordEncoder passwordHashEncoder,
                       JwtProvider jwtProvider) {
        this.customerRepo = userRepo;
        this.passwordHashEncoder = passwordHashEncoder;
        this.jwtProvider = jwtProvider;
    }

    public AuthModel register(AuthModel authModel) throws UserAlreadyExistsException {

        if(customerRepo.findByLogin(authModel.getLogin()) != null){
            throw new UserAlreadyExistsException("Customer exists");
        }

        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setLogin(authModel.getLogin());
        customerEntity.setPassword(authModel.getPassword());

        customerRepo.save(customerEntity);

        authModel.setToken(jwtProvider.generateAccessToken(authModel));
        return authModel;
    }

    public AuthModel authenticate(AuthModel authModel) throws UserNotFoundException {

        CustomerEntity customerEntity = customerRepo.findByLogin(authModel.getLogin());
        if( (customerEntity == null) || (!authModel.getPassword().equals(customerEntity.getPassword())) ){
            throw new UserNotFoundException("Customer not exists");
        }

        authModel.setToken(jwtProvider.generateAccessToken(authModel));
        return authModel;
    }

}
