package org.example.repos;

import org.example.entity.ProjectEntity;
import org.springframework.data.repository.ListCrudRepository;

public interface ProjectRepo extends ListCrudRepository<ProjectEntity, Long> {
    ProjectEntity findProjectByName(String name);
}
