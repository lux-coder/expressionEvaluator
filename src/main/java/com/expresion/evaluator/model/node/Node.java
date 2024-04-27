package com.expresion.evaluator.model.node;

import java.util.Map;

public abstract class Node {
    public abstract Object evaluate(Map<String, Object> context);
}

