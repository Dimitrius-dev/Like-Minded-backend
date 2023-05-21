package org.example.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.repository.Modifying;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
//import org.springframework.data.annotation.Id;

@Getter
@Setter
@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "login", unique = true)
    private String login;

    @Column(name = "password")
    private String password;

    //@OneToOne(mappedBy = "customerAdmin", cascade = CascadeType.ALL)
    @JsonBackReference
    @OneToMany(mappedBy = "authorCustomer", cascade = CascadeType.ALL)
    private List<Project> projects;

    @JsonBackReference
    @ManyToMany
    @JoinTable(
            name = "customer_project",
            joinColumns = { @JoinColumn(name = "customer_sub_id") },
            inverseJoinColumns = { @JoinColumn(name = "group_sub_id") }
    )
    private Set<Project> projectSubs = new HashSet<>();

//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//    private List<Thing> things;
}
