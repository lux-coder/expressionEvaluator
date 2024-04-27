package com.expresion.evaluator.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Expression {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @Column(name = "expression_value")
    private String value;
}
