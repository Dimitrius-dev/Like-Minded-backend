package org.example.controller;

import org.example.entity.CustomerEntity;
import org.example.entity.ProjectEntity;
import org.example.model.CustomerModel;
import org.example.model.MsgModel;
import org.example.model.ProjectModel;
import org.example.repos.CustomerRepo;
import org.example.repos.ProjectRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/")
public class CustomerController {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    private final CustomerRepo customerRepo;
    private final ProjectRepo projectRepo;

    CustomerController(CustomerRepo userRepo, ProjectRepo projectRepo){
        this.customerRepo = userRepo;
        this.projectRepo = projectRepo;
    }

    @GetMapping("/hello")
    public ResponseEntity hello() {
        try {
            return ResponseEntity.ok().body("hello user");//);UsersModel.toModel(userService.getAll()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("some error");
        }
    }

    @GetMapping("/customer/{login}")
    public ResponseEntity<CustomerModel> getCustomer(@PathVariable String login) {
        try {
            CustomerEntity customerEntity = customerRepo.findByLogin(login);
            if(customerEntity == null){
                throw new IOException("null person");
            }
            CustomerModel customerModel = new CustomerModel(customerRepo.findByLogin(login));

            return new ResponseEntity<>(customerModel, HttpStatus.OK);//);UsersModel.toModel(userService.getAll()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @PostMapping("/customer")
    public ResponseEntity<MsgModel> createCustomer(@RequestBody CustomerEntity customerEntity) {
        try {
            if(customerEntity == null){
                throw new IOException("sended null object");
            }
            log.info(customerEntity.toString());
            customerRepo.save(customerEntity);
            return new ResponseEntity<>(new MsgModel("user saved"), HttpStatus.OK);//);UsersModel.toModel(userService.getAll()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/customer/{login}/project/{name}")
    public ResponseEntity<MsgModel> deleteCustomerProject(@PathVariable String login, @PathVariable String name) {
        try {
            //Customer customer = customerRepo.findByLogin(login);
            //customer.getProjects().remove(
            //);
            projectRepo.delete(
                    projectRepo.findProjectByName(name)
            );

            return new ResponseEntity<>(new MsgModel("project deleted"), HttpStatus.OK);//);UsersModel.toModel(userService.getAll()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/customer/{login}/project")
    public ResponseEntity<MsgModel> createCustomerProject(@PathVariable String login,
                                                @RequestBody ProjectModel projectModel) {
        try {
            CustomerEntity customerEntity = customerRepo.findByLogin(login);

            ProjectEntity projectEntity = new ProjectEntity();

            projectEntity.setName(projectModel.getName());
            projectEntity.setDescription(projectModel.getDescription());
            projectEntity.setAuthorCustomerEntity(customerEntity);

            customerEntity.getProjectEntities().add(
                    projectEntity
            );
            customerRepo.save(customerEntity);

            return new ResponseEntity<>(new MsgModel("project added"), HttpStatus.OK);//);UsersModel.toModel(userService.getAll()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/customer/{login}/sub/{name}")
    public ResponseEntity<MsgModel> subProject(@PathVariable String login, @PathVariable String name) {
        try {
            CustomerEntity customerEntity = customerRepo.findByLogin(login);
            ProjectEntity projectEntity = projectRepo.findProjectByName(name);

            customerEntity.getProjectEntitySubs().add(projectEntity);
            customerRepo.save(customerEntity);

            return new ResponseEntity<>(new MsgModel("sub on project"), HttpStatus.OK);//);UsersModel.toModel(userService.getAll()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/customer/{login}/sub/{name}")
    public ResponseEntity<MsgModel> unsubProject(@PathVariable String login, @PathVariable String name) {
        try {
            CustomerEntity customerEntity = customerRepo.findByLogin(login);
            ProjectEntity projectEntity = projectRepo.findProjectByName(name);

            customerEntity.getProjectEntitySubs().remove(projectEntity);
            customerRepo.save(customerEntity);

            return new ResponseEntity<>(new MsgModel("unsub on project"), HttpStatus.OK);//);UsersModel.toModel(userService.getAll()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/customer/{login}/subs")
    public ResponseEntity<Set<ProjectModel>> getCustomerSubs(@PathVariable String login) {
        try {
            CustomerEntity customerEntity = customerRepo.findByLogin(login);

//            if(customerEntity == null){
//                throw new IOException("null projects");
//            }
            Set<ProjectModel> projectModels = new HashSet<>();
            for(ProjectEntity iter : customerEntity.getProjectEntitySubs()){
                projectModels.add(new ProjectModel(iter));
            }

            return new ResponseEntity<>(projectModels, HttpStatus.OK);//);UsersModel.toModel(userService.getAll()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/projects")
    public ResponseEntity<List<ProjectModel>> getProjects() {
        try {
            List<ProjectEntity> projectEntityList = projectRepo.findAll();

            List<ProjectModel> projectModelList = new ArrayList<>();

            for(ProjectEntity iter : projectEntityList){
                projectModelList.add(new ProjectModel(iter ));
            }

            return new ResponseEntity<>(projectModelList, HttpStatus.OK);//);UsersModel.toModel(userService.getAll()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/customer/{login}/projects")
    public ResponseEntity<List<ProjectModel>> getCustomerProjects(@PathVariable String login) {
        try {
            CustomerEntity customerEntity = customerRepo.findByLogin(login);

            List<ProjectModel> projectModelList = new ArrayList<>();

            for(ProjectEntity iter : customerEntity.getProjectEntities()){
                projectModelList.add(new ProjectModel(iter));
            }

            return new ResponseEntity<>(projectModelList, HttpStatus.OK);//);UsersModel.toModel(userService.getAll()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/project/{name}")
    public ResponseEntity<ProjectModel> getProject(@PathVariable String name) {
        try {
            ProjectEntity projectEntity = projectRepo.findProjectByName(name);
            if(projectEntity == null){
                throw new IOException("null project");
            }
            return new ResponseEntity<>(new ProjectModel(projectEntity), HttpStatus.OK);//);UsersModel.toModel(userService.getAll()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
