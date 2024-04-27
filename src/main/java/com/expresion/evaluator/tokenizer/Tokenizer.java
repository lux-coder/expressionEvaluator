package com.expresion.evaluator.tokenizer;

import com.expresion.evaluator.model.Token;
import com.expresion.evaluator.model.TokenType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tokenizer {

    private static final Logger log = LoggerFactory.getLogger(Tokenizer.class);

    private static final List<TokenType> skipTokenTypes = List.of(TokenType.WHITESPACE);

    private static final Pattern TOKEN_PATTERNS = Pattern.compile(
            "(\\s+)|" + // Whitespace (ignored)
            "(NULL|null)|" + // Null literal (case-insensitive)
            "(OR|AND|==|!=|<=|>=|<|>|&&|\\|\\|)|" + // Operators
            "([a-zA-Z_][a-zA-Z0-9_]*(\\.[a-zA-Z_][a-zA-Z0-9_]*)*)|" + // Identifiers including nested paths
            "(\\d+\\.\\d*|\\.\\d+|\\d+)|" + // Numbers: Matches integers and decimals
            "(\\()|" + // Opening parenthesis
            "(\\))|" + // Closing parenthesis
            "(\"([^\"]*)\")" // String literals
    );

    public static List<Token> tokenize(String input) throws ParseException {
        List<Token> tokens = new ArrayList<>();
        Matcher matcher = TOKEN_PATTERNS.matcher(input);
        int pos = 0;

        while (matcher.find()) {

            if (matcher.start() != pos) {
                throw new ParseException("Illegal character at position " + pos + ": " + input.charAt(pos), pos);
            }

            if (matcher.group(1) != null) {
            } else if (matcher.group(3) != null) {
                tokens.add(new Token(TokenType.OPERATOR, matcher.group(3)));
            } else if (matcher.group(4) != null) {
                tokens.add(new Token(TokenType.IDENTIFIER, matcher.group(4)));
            } else if (matcher.group(6) != null) {
                tokens.add(new Token(TokenType.NUMBER, matcher.group(6)));
            } else if (matcher.group(7) != null) {
                tokens.add(new Token(TokenType.PARENTHESIS_OPEN, matcher.group(7)));
            } else if (matcher.group(8) != null) {
                tokens.add(new Token(TokenType.PARENTHESIS_CLOSE, matcher.group(8)));
            } else if (matcher.group(9) != null) {
                tokens.add(new Token(TokenType.STRING, matcher.group(10)));
            } else if (matcher.group(2) != null) {
                tokens.add(new Token(TokenType.NULL, null));
            }
            else {
                throw new ParseException("Unmatched input at position " + pos + ": " + input.charAt(pos), pos);
            }
            pos = matcher.end();
        }

        return tokens;
    }
}
