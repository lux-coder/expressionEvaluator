package com.expresion.evaluator.controller;

import com.expresion.evaluator.exception.InvalidExpressionException;
import com.expresion.evaluator.model.Expression;
import com.expresion.evaluator.service.ExpressionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(summary = "Create a new expression", description = "Provide a name and value for the expression",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Name and value of the expression to create",
                    content = @Content(
                            mediaType = "text/plain",
                            examples = @ExampleObject(
                                    name = "CreateExpressionExample",
                                    value = "(customer.firstName == \"JOHN\" && customer.salary < 100) OR (customer.address != null && customer.address.city == \"Washington\")"
                            ),
                            schema = @Schema(implementation = Expression.class)
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful creation of expression"),
                    @ApiResponse(responseCode = "400", description = "Invalid expression data provided")
            }
    )
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
    @Operation(summary = "Evaluate an expression", description = "Provide expression ID and JSON object to evaluate against",
            parameters = {
                    @Parameter(name = "id", description = "The ID of the expression to evaluate", required = true,
                            example = "123")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "JSON object used to provide data for evaluating the expression",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "EvaluateExpressionExample",
                                    value = "{\"customer\": {\"firstName\": \"JOHN\", \"lastName\": \"DOE\", \"address\": {\"city\": \"Chicago\", \"zipCode\": 1234, \"street\": \"56th\", \"houseNumber\": 2345}, \"salary\": 99, \"type\": \"BUSINESS\"}}"
                            ),
                            schema = @Schema(implementation = Map.class)
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Expression evaluated successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "EvaluationSuccessExample",
                                            value = "{\"result\": true}"
                                    )
                            )),
                    @ApiResponse(responseCode = "400", description = "Invalid input or expression not found",
                            content = @Content(mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "EvaluationErrorExample",
                                            value = "{\"error\": \"Expression ID not found or invalid JSON data\"}"
                                    ))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error",
                            content = @Content(mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "InternalErrorExample",
                                            value = "{\"error\": \"Unexpected error occurred\"}"
                                    )))
            }
    )
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
