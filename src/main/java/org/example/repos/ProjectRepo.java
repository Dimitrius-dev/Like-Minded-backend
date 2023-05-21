package org.example.repos;

import org.example.entity.Customer;
import org.example.entity.Project;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface ProjectRepo extends ListCrudRepository<Project, Long> {
    Project findProjectByName(String name);
}
