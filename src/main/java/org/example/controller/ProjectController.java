package org.example.controller;

import org.example.entity.CustomerEntity;
import org.example.entity.ProjectEntity;
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
import java.util.List;

@RestController
@RequestMapping("/")
public class ProjectController {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    private final ProjectRepo projectRepo;

    private final CustomerRepo customerRepo;

    ProjectController(ProjectRepo projectRepo, CustomerRepo customerRepo){
        this.projectRepo = projectRepo;
        this.customerRepo = customerRepo;
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
//
//    @PostMapping("/customer")
//    public ResponseEntity createCustomer(@RequestBody Customer customer) {
//        try {
//            if(customer == null){
//                throw new IOException("sended null object");
//            }
//            log.info(customer.toString());
//            customerRepo.save(customer);
//            return ResponseEntity.ok().body("user saved");//);UsersModel.toModel(userService.getAll()));
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//    }
//
//    @GetMapping("/customer/{login}")
//    public ResponseEntity<Customer> getCustomerByLogin(@PathVariable String login) {
//        try {
//            Customer customer = customerRepo.findByLogin(login);
//            if(customer == null){
//                throw new IOException("null object");
//            }
//            //log.info(customer.toString());
//            return new ResponseEntity<>(customer, HttpStatus.OK);//);UsersModel.toModel(userService.getAll()));
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//    }
}
