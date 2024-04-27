package com.expresion.evaluator.evaluator;

import com.expresion.evaluator.exception.EvaluationException;
import com.expresion.evaluator.parser.ExpressionParser;
import com.expresion.evaluator.model.Token;
import com.expresion.evaluator.tokenizer.Tokenizer;
import com.expresion.evaluator.model.node.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public class ExpressionEvaluator {
    private static final Logger log = LoggerFactory.getLogger(ExpressionEvaluator.class);

        public static boolean evaluate(String expression, Map<String, Object> jsonData) throws ParseException {
            List<Token> tokens = tokenize(expression);
            Node ast = parse(tokens);
            return evaluateAST(ast, jsonData);
        }

        private static List<Token> tokenize(String expression) throws ParseException {
            return Tokenizer.tokenize(expression);
        }

        private static Node parse(List<Token> tokens) throws ParseException {
            return ExpressionParser.parse(tokens);
        }

        private static boolean evaluateAST(Node ast, Map<String, Object> jsonData) {
            try {
                // The root node's evaluate method recursive evaluation of the entire tree
                Object result = ast.evaluate(jsonData);
                log.debug("Expression evaluation result: {}", result);
                return (Boolean) result;
            } catch (ClassCastException e) {
                throw new IllegalArgumentException("Expression evaluation did not result in a boolean value.");
            } catch (RuntimeException e) {
                throw new EvaluationException("Error evaluating expression", e);
            }
        }

}
