package com.expresion.evaluator.integration;

import com.expresion.evaluator.exception.EvaluationException;
import com.expresion.evaluator.model.Token;
import com.expresion.evaluator.model.node.Node;
import com.expresion.evaluator.parser.ExpressionParser;
import com.expresion.evaluator.tokenizer.Tokenizer;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EEIntegrationTest {
    @Test
    public void testFullExpressionEvaluation() throws ParseException {
        String expression = "customer.firstName == \"JOHN\" && customer.salary > 100";
        List<Token> tokens = Tokenizer.tokenize(expression);
        Node ast = ExpressionParser.parse(tokens);
        Map<String, Object> context = new HashMap<>();
        context.put("customer", Map.of(
                "firstName", "JOHN",
                "salary", 150
        ));
        Boolean result = (Boolean) ast.evaluate(context);
        assertTrue(result);
    }

    @Test
    public void testErrorHandling() {
        String expression = "customer.salary > null";
        assertThrows(EvaluationException.class, () -> {
            List<Token> tokens = Tokenizer.tokenize(expression);
            Node ast = ExpressionParser.parse(tokens);
            Map<String, Object> context = Map.of("customer", Map.of("salary", 100));
            ast.evaluate(context);
        });
    }


}
