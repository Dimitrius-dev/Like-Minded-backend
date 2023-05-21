package org.example.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
//import org.springframework.data.annotation.Id;

@Getter
@Setter
@Entity
@Table(name = "project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "customer_author_id")
    private Customer authorCustomer;

    @JsonManagedReference
    @ManyToMany
    @JoinTable(
            name = "customer_project",
            joinColumns = { @JoinColumn(name = "group_sub_id") },
            inverseJoinColumns = { @JoinColumn(name = "customer_sub_id") }
    )
    private Set<Customer> customerSubs = new HashSet<>();


    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", authorCustomer=" + authorCustomer +
                ", customerSubs=" + customerSubs +
                '}';
    }
    //private List<> String password;

//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//    private List<Thing> things;
}