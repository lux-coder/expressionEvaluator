package com.expresion.evaluator.service;

import com.expresion.evaluator.exception.InvalidExpressionException;
import com.expresion.evaluator.model.Expression;
import com.expresion.evaluator.repository.ExpressionRepository;
import com.expresion.evaluator.evaluator.ExpressionEvaluator;
import com.expresion.evaluator.util.ExpressionValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Map;

@Service
public class ExpressionService {
    private static final Logger log = LoggerFactory.getLogger(ExpressionService.class);
    private final ExpressionRepository expressionRepository;

    public ExpressionService(ExpressionRepository expressionRepository) {
        this.expressionRepository = expressionRepository;
    }

    public Long createExpression(String name, String value) {
        log.debug("Creating expression with name: {} and value: {}", name, value);

        if (!ExpressionValidator.isValidExpression(value)) {
            log.error("Invalid expression syntax");
            throw new InvalidExpressionException("Invalid expression syntax");
        }

        Expression expression = new Expression();
        expression.setName(name);
        expression.setValue(value);
        expression = expressionRepository.save(expression);

        return expression.getId();
    }

    public Expression getExpressionById(Long expressionId) {
        log.debug("Getting expression by id: {}", expressionId);

        return expressionRepository.findById(expressionId)
                .orElseThrow(() -> new InvalidExpressionException("Expression not found"));            
    }

    public boolean evaluateExpression(Expression expression, Map<String, Object> jsonData) throws ParseException {
        log.debug("Evaluating expression: {} with json data: {}", expression, jsonData);

        String expressionValue = expression.getValue();
        for (Map.Entry<String, Object> entry : jsonData.entrySet()) {
            expressionValue = expressionValue.replace("{" + entry.getKey() + "}", entry.getValue().toString());
        }

        return ExpressionEvaluator.evaluate(expressionValue, jsonData);
    }
}
