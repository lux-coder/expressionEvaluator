package com.expresion.evaluator.model.node;

import com.expresion.evaluator.exception.EvaluationException;
import lombok.Data;

import java.util.Map;
import java.util.Objects;

@Data
public class BinaryOperatorNode extends Node {
    private Node left;
    private Node right;
    private String operator;

    public BinaryOperatorNode(Node left, Node right, String operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    @Override
    public Object evaluate(Map<String, Object> context) {
        try {

            // Implementation of evaluation logic for binary operations
            Object leftVal = left.evaluate(context);
            Object rightVal = right.evaluate(context);

            switch (operator) {
                case "&&", "AND":
                    if (leftVal == null || rightVal == null) {
                        return false;
                    } else if (!(leftVal instanceof Boolean && rightVal instanceof Boolean)) {
                        throw new EvaluationException("Logical AND requires boolean operands.");
                    }
                    return (Boolean) leftVal && (Boolean) rightVal;
                case "||", "OR":
                    if (leftVal == null || rightVal == null) {
                        return false;
                    } else if (!(leftVal instanceof Boolean && rightVal instanceof Boolean)) {
                        throw new EvaluationException("Logical OR requires boolean operands.");
                    }
                    return (Boolean) leftVal || (Boolean) rightVal;
                case "==":
                    return Objects.equals(leftVal, rightVal);
                case "!=":
                    return !Objects.equals(leftVal, rightVal);
                case ">":
                    if (!(leftVal instanceof Number && rightVal instanceof Number)) {
                        throw new EvaluationException("Comparison requires numeric operands.");
                    }
                    return ((Number) leftVal).doubleValue() > ((Number) rightVal).doubleValue();
                case "<":
                    if (!(leftVal instanceof Number && rightVal instanceof Number)) {
                        throw new EvaluationException("Comparison requires numeric operands.");
                    }
                    return ((Number) leftVal).doubleValue() < ((Number) rightVal).doubleValue();
                case ">=":
                    if (!(leftVal instanceof Number && rightVal instanceof Number)) {
                        throw new EvaluationException("Comparison requires numeric operands.");
                    }
                    return ((Number) leftVal).doubleValue() >= ((Number) rightVal).doubleValue();
                case "<=":
                    if (!(leftVal instanceof Number && rightVal instanceof Number)) {
                        throw new EvaluationException("Comparison requires numeric operands.");
                    }
                    return ((Number) leftVal).doubleValue() <= ((Number) rightVal).doubleValue();
                case "+":
                    if (!(leftVal instanceof Number && rightVal instanceof Number)) {
                        throw new EvaluationException("Addition requires numeric operands.");
                    }
                    return ((Number) leftVal).doubleValue() + ((Number) rightVal).doubleValue();
                case "-":
                    if (!(leftVal instanceof Number && rightVal instanceof Number)) {
                        throw new EvaluationException("Subtraction requires numeric operands.");
                    }
                    return ((Number) leftVal).doubleValue() - ((Number) rightVal).doubleValue();
                case "*":
                    if (!(leftVal instanceof Number && rightVal instanceof Number)) {
                        throw new EvaluationException("Multiplication requires numeric operands.");
                    }
                    return ((Number) leftVal).doubleValue() * ((Number) rightVal).doubleValue();
                case "/":
                    if (!(leftVal instanceof Number && rightVal instanceof Number)) {
                        throw new EvaluationException("Division requires numeric operands.");
                    }
                    return ((Number) leftVal).doubleValue() / ((Number) rightVal).doubleValue();
                default:
                    throw new UnsupportedOperationException("Unsupported operator: " + operator);
            }
        } catch (RuntimeException e) {
            throw new EvaluationException("Error evaluating binary operator node", e);
        }
    }
}
