package com.example.restapi.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="wallet")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long userid;
    private Integer balance = 0;
    private Integer balanceBefore = 0;
}
