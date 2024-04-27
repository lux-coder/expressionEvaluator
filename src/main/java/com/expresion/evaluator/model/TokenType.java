package com.expresion.evaluator.model;

public enum TokenType {
    NUMBER("\\b\\d+\\b"),
    STRING("\"[^\"]*\""),
    IDENTIFIER("[a-zA-Z_][a-zA-Z0-9_]*"),  // Variable names
    OPERATOR("==|!=|<=|>=|<|>|&&|\\|\\|"),
    WHITESPACE("\\s+"),
    PARENTHESIS_OPEN("\\("),
    PARENTHESIS_CLOSE("\\)"),
    OR("OR"),
    AND("AND"),
    NULL("NULL|null");

    public final String pattern;

    TokenType(String pattern) {
        this.pattern = pattern;
    }
}
