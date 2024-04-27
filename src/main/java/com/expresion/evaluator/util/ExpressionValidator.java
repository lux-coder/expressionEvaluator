package com.expresion.evaluator.util;

public class ExpressionValidator {

    public static boolean isValidExpression(String expression) {
        return hasBalancedParentheses(expression) && hasValidSyntax(expression);
    }

    private static boolean hasBalancedParentheses(String expression) {
        int balance = 0;
        for (char c : expression.toCharArray()) {
            if (c == '(') {
                balance++;
            } else if (c == ')') {
                balance--;
                if (balance < 0) {
                    return false; // a closing parenthesis ')' cannot occur before a matching opening '('
                }
            }
        }
        return balance == 0; // balanced if no unmatched parentheses remain
    }

    private static boolean hasValidSyntax(String expression) {
        String simplifiedExp = expression.replaceAll("[^\\w\\s()><=!&|]", ""); // Remove potential harmful characters
        return simplifiedExp.matches(".*[a-zA-Z]+.*"); // Check if it contains at least one alphabet (simple validation for a condition)
    }
}
