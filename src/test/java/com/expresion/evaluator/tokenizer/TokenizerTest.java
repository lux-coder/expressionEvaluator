package com.expresion.evaluator.tokenizer;

import com.expresion.evaluator.model.Token;
import com.expresion.evaluator.model.TokenType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;
import java.util.List;


public class TokenizerTest {

    @Test
    public void testTokenizer() throws ParseException {
        String input = "customer.firstName == \"JOHN\" && customer.age >= 30";
        List<Token> tokens = Tokenizer.tokenize(input);

        // Assert that the number of tokens is as expected
        assertEquals(7, tokens.size(), "Should correctly tokenize the input into 7 tokens.");

        // Check identifier
        assertEquals(TokenType.IDENTIFIER, tokens.get(0).type());
        assertEquals("customer.firstName", tokens.get(0).value());

        // Check operator (==)
        assertEquals(TokenType.OPERATOR, tokens.get(1).type());
        assertEquals("==", tokens.get(1).value());

        // Check string literal ('JOHN')
        assertEquals(TokenType.STRING, tokens.get(2).type());
        assertEquals("JOHN", tokens.get(2).value());

        // Check logical operator (&&)
        assertEquals(TokenType.OPERATOR, tokens.get(3).type());
        assertEquals("&&", tokens.get(3).value());

        // Check identifier
        assertEquals(TokenType.IDENTIFIER, tokens.get(4).type());
        assertEquals("customer.age", tokens.get(4).value());

        // Check operator (>=)
        assertEquals(TokenType.OPERATOR, tokens.get(5).type());
        assertEquals(">=", tokens.get(5).value());

        // Check numeric literal (30)
        assertEquals(TokenType.NUMBER, tokens.get(6).type());
        assertEquals("30", tokens.get(6).value());

    }
}
