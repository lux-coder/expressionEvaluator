package com.expresion.evaluator.parser;

import com.expresion.evaluator.model.Token;
import com.expresion.evaluator.model.TokenType;
import com.expresion.evaluator.model.node.BinaryOperatorNode;
import com.expresion.evaluator.model.node.LiteralNode;
import com.expresion.evaluator.model.node.Node;
import com.expresion.evaluator.model.node.VariableNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.List;
import java.util.Stack;

public class ExpressionParser {

    private static final Logger log = LoggerFactory.getLogger(ExpressionParser.class);
    public static Node parse(List<Token> tokens) throws ParseException {
        Stack<Node> nodeStack = new Stack<>();
        Stack<Token> operatorStack = new Stack<>();

        for (Token token : tokens) {
            switch (token.type()) {
                case NUMBER:
                    nodeStack.push(new LiteralNode(Double.parseDouble(token.value())));
                    break;
                case STRING:
                    nodeStack.push(new LiteralNode(token.value()));
                    break;
                case NULL:
                    nodeStack.push(new LiteralNode(null));
                    break;
                case IDENTIFIER:
                    nodeStack.push(new VariableNode(token.value()));
                    break;
                case OPERATOR, OR, AND:
                    while (!operatorStack.isEmpty() && hasPrecedence(token, operatorStack.peek())) {
                        if (nodeStack.size() < 2) {
                            throw new ParseException("Not enough operands for the operator." + operatorStack.peek().value(), 0);
                        }
                        Token operator = operatorStack.pop();
                        Node right = nodeStack.pop();
                        Node left = nodeStack.pop();
                        nodeStack.push(new BinaryOperatorNode(left, right, operator.value()));
                    }
                    operatorStack.push(token);
                    break;
                case PARENTHESIS_OPEN:
                    operatorStack.push(token);
                    break;
                case PARENTHESIS_CLOSE:
                    while (!operatorStack.isEmpty() && operatorStack.peek().type() != TokenType.PARENTHESIS_OPEN) {
                        if (nodeStack.size() < 2) {
                            throw new ParseException("Not enough operands inside parentheses." + operatorStack.peek().value(), 0);
                        }
                        Token operator = operatorStack.pop();
                        Node right = nodeStack.pop();
                        Node left = nodeStack.pop();
                        nodeStack.push(new BinaryOperatorNode(left, right, operator.value()));
                    }
                    if (operatorStack.isEmpty()) {
                        throw new ParseException("Mismatched parentheses.", 0);
                    }
                    operatorStack.pop(); // Pop the '('
                    break;
                default:
                    throw new ParseException("Unexpected token: " + token.type(), 0);
            }
        }

        while (!operatorStack.isEmpty()) {
            Token operator = operatorStack.pop();
            Node right = nodeStack.pop();
            Node left = nodeStack.pop();
            nodeStack.push(new BinaryOperatorNode(left, right, operator.value()));
        }

        return nodeStack.pop();
    }

    private static boolean hasPrecedence(Token current, Token onStack) {
        if (current.type() == TokenType.OPERATOR && onStack.type() == TokenType.OPERATOR) {
            int currentPrec = getPrecedence(current.value());
            int onStackPrec = getPrecedence(onStack.value());
            return currentPrec <= onStackPrec;
        }
        return false;
    }

    private static int getPrecedence(String operator) {
        return switch (operator) {
            case "||", "OR" -> 1;
            case "&&", "AND" -> 2;
            case "==", "!=" -> 3;
            case "<", ">", "<=", ">=" -> 4;
            default -> Integer.MAX_VALUE; // default to the lowest precedence
        };
    }

}
