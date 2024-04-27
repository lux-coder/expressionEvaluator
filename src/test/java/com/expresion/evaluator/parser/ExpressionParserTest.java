package com.expresion.evaluator.parser;

import com.expresion.evaluator.model.Token;
import com.expresion.evaluator.model.TokenType;
import com.expresion.evaluator.model.node.BinaryOperatorNode;
import com.expresion.evaluator.model.node.LiteralNode;
import com.expresion.evaluator.model.node.Node;
import com.expresion.evaluator.model.node.VariableNode;
import com.expresion.evaluator.tokenizer.Tokenizer;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExpressionParserTest {
    @Test
    public void testParser() throws ParseException {
        List<Token> tokens = Arrays.asList(
                new Token(TokenType.IDENTIFIER, "customer.firstName"),
                new Token(TokenType.OPERATOR, "=="),
                new Token(TokenType.STRING, "JOHN")
        );
        Node ast = ExpressionParser.parse(tokens);
        assertTrue(ast instanceof BinaryOperatorNode);
    }

    @Test
    public void testAstStructure() throws ParseException {
        // Tokenize and parse the expression
        String input = "customer.firstName == \"JOHN\"";
        List<Token> tokens = Tokenizer.tokenize(input);
        Node ast = ExpressionParser.parse(tokens);

        // Assert the top-level node is a BinaryOperatorNode
        assertTrue(ast instanceof BinaryOperatorNode, "Root should be a BinaryOperatorNode");

        BinaryOperatorNode binOpNode = (BinaryOperatorNode) ast;

        // Check the operator
        assertEquals("==", binOpNode.getOperator(), "Operator should be ==");

        // Verify the left side of the expression
        assertTrue(binOpNode.getLeft() instanceof VariableNode, "Left side should be a VariableNode");
        VariableNode leftVar = (VariableNode) binOpNode.getLeft();
        assertEquals("customer.firstName", leftVar.getVariablePath(), "Left variable name should be 'customer.firstName'");

        // Verify the right side of the expression
        assertTrue(binOpNode.getRight() instanceof LiteralNode, "Right side should be a LiteralNode");
        LiteralNode rightLiteral = (LiteralNode) binOpNode.getRight();
        assertEquals("JOHN", rightLiteral.getValue(), "Right literal value should be 'JOHN'");
    }
}
