package com.expresion.evaluator.controller;

import com.expresion.evaluator.exception.InvalidExpressionException;
import com.expresion.evaluator.model.Expression;
import com.expresion.evaluator.service.ExpressionService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Map;

@RestController
@RequestMapping("/expression")
public class ExpressionController {

    private static final Logger log = LoggerFactory.getLogger(ExpressionController.class);

    private final ExpressionService expressionService;

    public ExpressionController(ExpressionService expressionService) {
        this.expressionService = expressionService;
    }

    @PostMapping("/create")
    @Operation(summary = "Create a new expression", description = "Provide a name and value for the expression")
    public ResponseEntity<?> createExpression(@RequestParam String name, @RequestBody String value) {
        log.debug("Received expression with name: {} and value: {}", name, value);
        try {
            Long expressionId = expressionService.createExpression(name, value);
            return ResponseEntity.ok(expressionId);
        } catch (InvalidExpressionException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/evaluate/{id}")
    @Operation(summary = "Evaluate an expression", description = "Provide expression ID and JSON object to evaluate against")
    public ResponseEntity<?> evaluateExpression(@PathVariable("id") Long id, @RequestBody Map<String, Object> jsonData) {
        log.debug("Received expression to evaluate: {}", jsonData);
        try {
            Expression expression = expressionService.getExpressionById(id);
            boolean result = expressionService.evaluateExpression(expression, jsonData);
            return ResponseEntity.ok(result);
        } catch (InvalidExpressionException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
