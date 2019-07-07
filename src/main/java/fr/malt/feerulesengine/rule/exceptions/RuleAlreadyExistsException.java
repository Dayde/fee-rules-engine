package fr.malt.feerulesengine.rule.exceptions;

public class RuleAlreadyExistsException extends RuntimeException {
    public RuleAlreadyExistsException(String message) {
        super(message);
    }
}
