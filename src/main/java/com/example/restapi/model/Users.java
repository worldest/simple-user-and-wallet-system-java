package com.example.restapi.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;
    public String fullname;
    @Column(unique = true)
    public String email;
    public String password;

}
