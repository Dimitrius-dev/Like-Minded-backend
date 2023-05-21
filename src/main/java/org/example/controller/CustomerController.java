package org.example.controller;

import org.example.entity.Customer;
import org.example.entity.Project;
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

//    @GetMapping("/hello")
//    public ResponseEntity hello() {
//        try {
//            return ResponseEntity.ok().body("hello user");//);UsersModel.toModel(userService.getAll()));
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("some error");
//        }
//    }

    @GetMapping("/customer/{login}")
    public ResponseEntity<Customer> getCustomer(@PathVariable String login) {
        try {
            Customer customer = customerRepo.findByLogin(login);
            if(customer == null){
                throw new IOException("null person");
            }
            return new ResponseEntity<>(customer, HttpStatus.OK);//);UsersModel.toModel(userService.getAll()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @PostMapping("/customer")
    public ResponseEntity createCustomer(@RequestBody Customer customer) {
        try {
            if(customer == null){
                throw new IOException("sended null object");
            }
            log.info(customer.toString());
            customerRepo.save(customer);
            return ResponseEntity.ok().body("user saved");//);UsersModel.toModel(userService.getAll()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/customer/{login}/project/{name}")
    public ResponseEntity deleteCustomerProject(@PathVariable String login, @PathVariable String name) {
        try {
            //Customer customer = customerRepo.findByLogin(login);
            //customer.getProjects().remove(
            //);
            projectRepo.delete(
                    projectRepo.findProjectByName(name)
            );

            return ResponseEntity.ok().body("project deleted");//);UsersModel.toModel(userService.getAll()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/customer/{login}/project")
    public ResponseEntity createCustomerProject(@PathVariable String login,
                                                @RequestBody Project project) {
        try {
            Customer customer = customerRepo.findByLogin(login);
            project.setAuthorCustomer(customer);

            customer.getProjects().add(
                    project
            );
            customerRepo.save(customer);

            return ResponseEntity.ok().body("project added");//);UsersModel.toModel(userService.getAll()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/customer/{login}/sub/{name}")
    public ResponseEntity subProject(@PathVariable String login, @PathVariable String name) {
        try {
            Customer customer = customerRepo.findByLogin(login);
            Project project = projectRepo.findProjectByName(name);

            customer.getProjectSubs().add(project);
            customerRepo.save(customer);

            return ResponseEntity.ok().body("sub on project");//);UsersModel.toModel(userService.getAll()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/customer/{login}/sub/{name}")
    public ResponseEntity unsubProject(@PathVariable String login, @PathVariable String name) {
        try {
            Customer customer = customerRepo.findByLogin(login);
            Project project = projectRepo.findProjectByName(name);

            customer.getProjectSubs().remove(project);
            customerRepo.save(customer);

            return ResponseEntity.ok().body("unsub on project");//);UsersModel.toModel(userService.getAll()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/customer/{login}/subs")
    public ResponseEntity<Set<Project>> getCustomerSubs(@PathVariable String login) {
        try {
            Customer customer = customerRepo.findByLogin(login);
            return new ResponseEntity<>(customer.getProjectSubs(), HttpStatus.OK);//);UsersModel.toModel(userService.getAll()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/projects")
    public ResponseEntity<List<Project>> getProjects() {
        try {
            List<Project> projectList = projectRepo.findAll();
            log.info(projectList.toString());

            for(Project p : projectList){
                log.info(p.toString());
            }

            List<Project> list = new ArrayList<>();
            //list.add(new Project());

            return new ResponseEntity<>(projectList, HttpStatus.OK);//);UsersModel.toModel(userService.getAll()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/customer/{login}/projects")
    public ResponseEntity<List<Project>> getCustomerProjects(@PathVariable String login) {
        try {
            Customer customer = customerRepo.findByLogin(login);
            return new ResponseEntity<>(customer.getProjects(), HttpStatus.OK);//);UsersModel.toModel(userService.getAll()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/project/{name}")
    public ResponseEntity<Project> getProject(@PathVariable String name) {
        try {
            Project project = projectRepo.findProjectByName(name);
            if(project == null){
                throw new IOException("null project");
            }
            return new ResponseEntity<>(project, HttpStatus.OK);//);UsersModel.toModel(userService.getAll()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
