package com.expresion.evaluator.repository;

import com.expresion.evaluator.model.Expression;
import org.springframework.data.repository.CrudRepository;

public interface ExpressionRepository extends CrudRepository<Expression, Long> {
}
