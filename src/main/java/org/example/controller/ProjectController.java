package org.example.controller;

import org.example.repos.ProjectRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class ProjectController {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    private final ProjectRepo projectRepo;

    ProjectController(ProjectRepo projectRepo){
        this.projectRepo = projectRepo;
    }

    @PostMapping("/hello")
    public ResponseEntity createProject() {
        try {
            return ResponseEntity.ok().body("hello user");//);UsersModel.toModel(userService.getAll()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("some error");
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
