package com.example.restapi.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name="lastlogin")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Auth {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;
    public String email;
    public String password;
    public Long userid;
    public java.util.Date lastLogin;
}
