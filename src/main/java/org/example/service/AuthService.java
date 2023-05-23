package org.example.service;

import org.example.entity.Customer;
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
        Customer customer = new Customer();
        customer.setLogin(authModel.getLogin());
        customer.setPassword(authModel.getPassword());

        customerRepo.save(customer);

        authModel.setToken(jwtProvider.generateAccessToken(authModel));
        return authModel;
    }

    public AuthModel authenticate(AuthModel authModel) throws UserNotFoundException {

        if(customerRepo.findByLogin(authModel.getLogin()) == null){
            throw new UserNotFoundException("Customer exists");
        }

        authModel.setToken(jwtProvider.generateAccessToken(authModel));
        return authModel;
    }

}
