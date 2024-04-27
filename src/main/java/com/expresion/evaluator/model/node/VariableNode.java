package com.expresion.evaluator.model.node;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

@Data
public class VariableNode extends Node {

    private static final Logger log = LoggerFactory.getLogger(VariableNode.class);
    private String variablePath;

    public VariableNode(String variablePath) {
        this.variablePath = variablePath;
    }

    @Override
    public Object evaluate(Map<String, Object> context) {
        log.debug("Context: {}", context);

        String[] pathSegments = variablePath.split("\\.");
        Object current = context;

        for (String segment : pathSegments) {
            if (current instanceof Map<?, ?>) {
                current = ((Map<String, Object>) current).get(segment);
                if (current == null) {
                    throw new IllegalArgumentException("Variable " + segment + " not found in context at " + variablePath);
                }
            } else {
                throw new IllegalArgumentException("Path " + variablePath + " cannot be navigated on non-map object at segment " + segment);
            }
        }
        return current;
    }
}
