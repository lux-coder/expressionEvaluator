package com.expresion.evaluator.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Schema(description = "Details about the expression")
public class Expression {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(description = "The unique ID of the expression")
    private Long id;
    @Schema(description = "The name of the expression")
    private String name;
    @Column(name = "expression_value")
    @Schema(description = "The logical expression string")
    private String value;
}
