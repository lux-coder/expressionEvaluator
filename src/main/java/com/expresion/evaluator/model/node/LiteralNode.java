package com.expresion.evaluator.model.node;

import lombok.Data;

import java.util.Map;

@Data
public class LiteralNode extends Node {
    private Object value;

    public LiteralNode(Object value) {
        this.value = value;
    }

    @Override
    public Object evaluate(Map<String, Object> context) {
        return value;
    }
}
