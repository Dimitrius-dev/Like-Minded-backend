package org.example.model;

import org.example.entity.ProjectEntity;

public class ProjectModel {

    public ProjectModel() {
    }
    private String name;

    private String description;

    private CustomerModel authorCustomer;

    public CustomerModel getAuthorCustomer() {
        return authorCustomer;
    }

    public void setAuthorCustomer(CustomerModel authorCustomer) {
        this.authorCustomer = authorCustomer;
    }

    public ProjectModel(String name, String description, CustomerModel authorCustomer) {
        this.name = name;
        this.description = description;
        this.authorCustomer = authorCustomer;
    }

    public ProjectModel(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public ProjectModel(ProjectEntity projectEntity) {
        this.name = projectEntity.getName();
        this.description = projectEntity.getDescription();
        this.authorCustomer = new CustomerModel(projectEntity.getAuthorCustomerEntity());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
