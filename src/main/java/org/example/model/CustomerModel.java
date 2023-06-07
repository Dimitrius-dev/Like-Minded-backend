package org.example.model;

import org.example.entity.CustomerEntity;

public class CustomerModel {
    public CustomerModel() {
    }

    private String login;

    private String password;

    public CustomerModel(String login, String password) {
        this.login = login;
        this.password = password;
    }
    public CustomerModel(CustomerEntity customerEntity) {
        this.login = customerEntity.getLogin();
        this.password = customerEntity.getPassword();
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
