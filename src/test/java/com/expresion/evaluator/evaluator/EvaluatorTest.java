package com.expresion.evaluator.evaluator;

import com.expresion.evaluator.exception.EvaluationException;
import com.expresion.evaluator.model.node.BinaryOperatorNode;
import com.expresion.evaluator.model.node.LiteralNode;
import com.expresion.evaluator.model.node.Node;
import com.expresion.evaluator.model.node.VariableNode;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EvaluatorTest {

    @Test
    public void testEvaluator() {
        Node ast = new BinaryOperatorNode(
                new VariableNode("customer.firstName"),
                new LiteralNode("JOHN"),
                "=="
        );
        Map<String, Object> context = Map.of("customer", Map.of("firstName", "JOHN"));
        Boolean result = (Boolean) ast.evaluate(context);
        assertTrue(result);
    }

    @Test
    public void testEvaluationErrorWithTypeMismatch() {
        Node leftNode = new LiteralNode(true);
        Node rightNode = new LiteralNode(5);  // Incorrect type, expecting Boolean
        BinaryOperatorNode ast = new BinaryOperatorNode(leftNode, rightNode, "&&");

        assertThrows(EvaluationException.class, () -> ast.evaluate(new HashMap<>()));
    }


}
